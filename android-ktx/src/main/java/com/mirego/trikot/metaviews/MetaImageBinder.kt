package com.mirego.trikot.metaviews

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mirego.trikot.metaviews.mutable.MutableMetaImage
import com.mirego.trikot.metaviews.properties.ImageState
import com.mirego.trikot.metaviews.properties.MetaSelector
import com.mirego.trikot.streams.android.ktx.observe
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.cancellable.CancellableManagerProvider
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.streams.reactive.subscribe
import com.mirego.trikot.streams.reactive.withCancellableManager
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Transformation

object MetaImageBinder {
    private val NoMetaImage = MutableMetaImage { _, _ -> Publishers.behaviorSubject() }
        .apply { hidden = true.just() } as MetaImage

    @JvmStatic
    @BindingAdapter("meta_view", "lifecycleOwnerWrapper")
    fun bind(
        imageView: ImageView,
        metaImage: MetaImage?,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        bind(imageView, metaImage, null, lifecycleOwnerWrapper)
    }

    @JvmStatic
    @BindingAdapter("meta_view", "transformation", "lifecycleOwnerWrapper")
    fun bind(
        imageView: ImageView,
        metaImage: MetaImage?,
        transformation: Transformation?,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        (metaImage ?: NoMetaImage).let {

            imageView.bindMetaView(metaImage, lifecycleOwnerWrapper)

            imageView.viewTreeObserver.addOnPreDrawListener(
                ViewLoaderPreDrawListener(imageView) { width: ImageWidth, height: ImageHeight ->
                    it.imageFlow(width, height)
                        .withCancellableManager()
                        .observe(lifecycleOwnerWrapper.lifecycleOwner) { (manager, imageFlow) ->
                            processImageFlow(
                                it,
                                imageFlow,
                                imageView,
                                transformation,
                                manager
                            )
                        }
                }
            )
        }
    }

    private fun processImageFlow(
        metaImage: MetaImage,
        imageFlow: ImageFlow,
        imageView: ImageView,
        transformation: Transformation?,
        cancellableManager: CancellableManager
    ) {
        imageFlow.imageResource?.asDrawable(
            imageView.context,
            imageFlow.tintColor?.let { MetaSelector(imageFlow.tintColor) })?.let {
            imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            imageView.setImageDrawable(it)
            metaImage.setImageState(ImageState.SUCCESS)
        } ?: run {
            imageFlow.url?.let { url ->
                var requestCreator: RequestCreator? = null
                val resourceId = imageFlow.placeholderImageResource?.resourceId(imageView.context)
                resourceId?.let { placeholderId ->
                    requestCreator = Picasso.get().load(url).placeholder(placeholderId)
                } ?: run {
                    requestCreator = Picasso.get().load(url)
                }

                transformation?.let { requestCreator?.transform(it) }
                requestCreator?.into(imageView, object : Callback {
                    override fun onSuccess() {
                        imageFlow.onSuccess?.let { onSuccessPublisher ->
                            val cancellableManagerProvider =
                                CancellableManagerProvider()
                                    .also { cancellable ->
                                        cancellableManager.add(cancellable)
                                    }
                            onSuccessPublisher.subscribe(cancellableManager) {
                                processImageFlow(
                                    metaImage,
                                    it,
                                    imageView,
                                    transformation,
                                    cancellableManagerProvider.cancelPreviousAndCreate()
                                )
                            }
                        } ?: run {
                            metaImage.setImageState(ImageState.SUCCESS)
                        }
                    }

                    override fun onError(e: Exception?) {
                        imageFlow.onError?.let { onErrorPublisher ->
                            val cancellableManagerProvider =
                                CancellableManagerProvider()
                                    .also { cancellable ->
                                        cancellableManager.add(cancellable)
                                    }

                            onErrorPublisher.subscribe(cancellableManager) {
                                processImageFlow(
                                    metaImage,
                                    it,
                                    imageView,
                                    transformation,
                                    cancellableManagerProvider.cancelPreviousAndCreate()
                                )
                            }
                        } ?: run {
                            metaImage.setImageState(ImageState.ERROR)
                        }
                    }
                })
            } ?: run {
                imageFlow.placeholderImageResource?.asDrawable(imageView.context)?.let {
                    imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                    imageView.setImageDrawable(it)
                }
            }
        }
    }
}
