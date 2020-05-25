package com.mirego.trikot.viewmodels

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mirego.trikot.viewmodels.mutable.MutableImageViewModel
import com.mirego.trikot.viewmodels.properties.ImageState
import com.mirego.trikot.viewmodels.properties.StateSelector
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

object ImageViewModelBinder {
    private val NoImageViewModel = MutableImageViewModel { _, _ -> Publishers.behaviorSubject() }
        .apply { hidden = true.just() } as ImageViewModel

    @JvmStatic
    @BindingAdapter("view_model", "lifecycleOwnerWrapper")
    fun bind(
        imageView: ImageView,
        imageViewModel: ImageViewModel?,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        bind(imageView, imageViewModel, null, lifecycleOwnerWrapper)
    }

    @JvmStatic
    @BindingAdapter("view_model", "transformation", "lifecycleOwnerWrapper")
    fun bind(
        imageView: ImageView,
        imageViewModel: ImageViewModel?,
        transformation: Transformation?,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        (imageViewModel ?: NoImageViewModel).let {

            imageView.bindViewModel(imageViewModel, lifecycleOwnerWrapper)

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
        imageViewModel: ImageViewModel,
        imageFlow: ImageFlow,
        imageView: ImageView,
        transformation: Transformation?,
        cancellableManager: CancellableManager
    ) {
        imageFlow.imageResource?.asDrawable(
            imageView.context,
            imageFlow.tintColor?.let { StateSelector(imageFlow.tintColor) })?.let {
            imageView.setImageDrawable(it)
            imageViewModel.setImageState(ImageState.SUCCESS)
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
                                    imageViewModel,
                                    it,
                                    imageView,
                                    transformation,
                                    cancellableManagerProvider.cancelPreviousAndCreate()
                                )
                            }
                        } ?: run {
                            imageViewModel.setImageState(ImageState.SUCCESS)
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
                                    imageViewModel,
                                    it,
                                    imageView,
                                    transformation,
                                    cancellableManagerProvider.cancelPreviousAndCreate()
                                )
                            }
                        } ?: run {
                            imageViewModel.setImageState(ImageState.ERROR)
                        }
                    }
                })
            } ?: run {
                imageFlow.placeholderImageResource?.asDrawable(imageView.context)?.let {
                    imageView.setImageDrawable(it)
                }
            }
        }
    }
}
