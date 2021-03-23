package com.mirego.trikot.viewmodels

import android.widget.ImageView
import androidx.core.view.OneShotPreDrawListener
import androidx.databinding.BindingAdapter
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.cancellable.CancellableManagerProvider
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.streams.reactive.observe
import com.mirego.trikot.streams.reactive.subscribe
import com.mirego.trikot.streams.reactive.withCancellableManager
import com.mirego.trikot.viewmodels.mutable.MutableImageViewModel
import com.mirego.trikot.viewmodels.properties.ImageState
import com.mirego.trikot.viewmodels.properties.StateSelector
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Transformation

object ImageViewModelBinder {
    private val NoImageViewModel = MutableImageViewModel { _, _ -> Publishers.behaviorSubject() }
        .apply { hidden = true.just() } as ImageViewModel

    @JvmStatic
    @BindingAdapter(value = ["view_model", "lifecycleOwnerWrapper", "transformation", "placeholderScaleType"], requireAll = false)
    fun bind(
        imageView: ImageView,
        imageViewModel: ImageViewModel?,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper,
        transformation: Transformation? = null,
        placeholderScaleType: ImageView.ScaleType? = null
    ) {
        (imageViewModel ?: NoImageViewModel).let {

            imageView.bindViewModel(imageViewModel, lifecycleOwnerWrapper)

            val originalScaleType = imageView.scaleType

            OneShotPreDrawListener.add(imageView) {
                it.imageFlow(ImageWidth(imageView.width), ImageHeight(imageView.height))
                    .withCancellableManager()
                    .observe(lifecycleOwnerWrapper.lifecycleOwner) { (manager, imageFlow) ->
                        processImageFlow(
                            it,
                            imageFlow,
                            imageView,
                            transformation,
                            originalScaleType,
                            placeholderScaleType,
                            manager
                        )
                    }
            }
        }
    }

    private fun processImageFlow(
        imageViewModel: ImageViewModel,
        imageFlow: ImageFlow,
        imageView: ImageView,
        transformation: Transformation?,
        originalScaleType: ImageView.ScaleType,
        placeholderScaleType: ImageView.ScaleType?,
        cancellableManager: CancellableManager
    ) {
        imageFlow.imageResource?.asDrawable(
            imageView.context,
            imageFlow.tintColor?.let { StateSelector(imageFlow.tintColor) }
        )?.let {
            imageView.setImageDrawable(it)
            imageViewModel.setImageState(ImageState.SUCCESS)
        } ?: run {
            imageFlow.url?.let { url ->
                var requestCreator: RequestCreator? = null
                val resourceId = imageFlow.placeholderImageResource?.resourceId(imageView.context)
                resourceId?.let { placeholderId ->
                    placeholderScaleType?.let { imageView.scaleType = it }
                    requestCreator = Picasso.get().load(url).placeholder(placeholderId)
                } ?: run {
                    requestCreator = Picasso.get().load(url)
                }

                transformation?.let { requestCreator?.transform(it) }
                requestCreator?.into(
                    imageView,
                    object : Callback {
                        override fun onSuccess() {
                            imageView.scaleType = originalScaleType
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
                                        originalScaleType,
                                        placeholderScaleType,
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
                                        originalScaleType,
                                        placeholderScaleType,
                                        cancellableManagerProvider.cancelPreviousAndCreate()
                                    )
                                }
                            } ?: run {
                                imageViewModel.setImageState(ImageState.ERROR)
                            }
                        }
                    }
                )
            } ?: run {
                imageFlow.placeholderImageResource?.asDrawable(imageView.context)?.let {
                    placeholderScaleType?.let { imageView.scaleType = it }
                    imageView.setImageDrawable(it)
                }
            }
        }
    }
}
