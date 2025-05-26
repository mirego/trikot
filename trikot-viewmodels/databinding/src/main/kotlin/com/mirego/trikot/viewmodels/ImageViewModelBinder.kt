package com.mirego.trikot.viewmodels

import android.widget.ImageView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.OneShotPreDrawListener
import androidx.databinding.BindingAdapter
import coil3.ImageLoader
import coil3.asDrawable
import coil3.imageLoader
import coil3.request.ImageRequest
import coil3.request.placeholder
import coil3.request.transformations
import coil3.transform.Transformation
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
import com.mirego.trikot.viewmodels.utils.BindingUtils

object ImageViewModelBinder {
    private val NoImageViewModel = MutableImageViewModel { _, _ -> Publishers.behaviorSubject() }
        .apply { hidden = true.just() } as ImageViewModel

    @JvmStatic
    @BindingAdapter(
        value = ["view_model", "imageLoader", "lifecycleOwnerWrapper", "transformation", "placeholderScaleType"],
        requireAll = false
    )
    fun bind(
        imageView: ImageView,
        imageViewModel: ImageViewModel?,
        imageLoader: ImageLoader? = null,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper? = null,
        transformation: Transformation? = null,
        placeholderScaleType: ImageView.ScaleType? = null
    ) {
        bind(
            imageView,
            imageViewModel,
            imageLoader,
            lifecycleOwnerWrapper,
            transformations = transformation?.let { listOf(it) },
            placeholderScaleType = placeholderScaleType
        )
    }

    @JvmStatic
    @BindingAdapter(
        value = ["view_model", "imageLoader", "lifecycleOwnerWrapper", "transformations", "placeholderScaleType"],
        requireAll = false
    )
    fun bind(
        imageView: ImageView,
        imageViewModel: ImageViewModel?,
        imageLoader: ImageLoader? = null,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper? = null,
        transformations: List<Transformation>? = null,
        placeholderScaleType: ImageView.ScaleType? = null
    ) {
        val safeLifecycleOwnerWrapper = lifecycleOwnerWrapper ?: BindingUtils.getLifecycleOwnerWrapperFromView(imageView)
        (imageViewModel ?: NoImageViewModel).let {
            imageView.bindViewModel(imageViewModel, safeLifecycleOwnerWrapper)

            val originalScaleType = imageView.scaleType

            OneShotPreDrawListener.add(imageView) {
                it.imageFlow(ImageWidth(imageView.width), ImageHeight(imageView.height))
                    .withCancellableManager()
                    .observe(safeLifecycleOwnerWrapper.lifecycleOwner) { (manager, imageFlow) ->
                        processImageFlow(
                            it,
                            imageFlow,
                            imageView,
                            transformations,
                            originalScaleType,
                            placeholderScaleType,
                            manager,
                            imageLoader ?: imageView.context.imageLoader
                        )
                    }
            }
        }
    }

    private fun processImageFlow(
        imageViewModel: ImageViewModel,
        imageFlow: ImageFlow,
        imageView: ImageView,
        transformations: List<Transformation>?,
        originalScaleType: ImageView.ScaleType,
        placeholderScaleType: ImageView.ScaleType?,
        cancellableManager: CancellableManager,
        imageLoader: ImageLoader
    ) {
        val imageResourceId = imageFlow.imageResource?.resourceId(imageView.context)
        if (!transformations.isNullOrEmpty() && imageResourceId != null) {
            val request = ImageRequest.Builder(imageView.context)
                .data(imageResourceId)
                .target(
                    onSuccess = { result ->
                        imageView.setImageDrawable(result.asDrawable(imageView.resources))
                        imageFlow.tintColor?.let {
                            DrawableCompat.setTint(imageView.drawable.mutate(), it.toIntColor())
                        }
                        imageViewModel.setImageState(ImageState.SUCCESS)
                    },
                    onError = {
                        imageViewModel.setImageState(ImageState.ERROR)
                    }
                )
                .transformations(transformations)
                .build()

            imageLoader.enqueue(request)
        } else {
            imageFlow.imageResource?.asDrawable(
                imageView.context,
                imageFlow.tintColor?.let { StateSelector(imageFlow.tintColor) }
            )?.let {
                imageView.setImageDrawable(it)
                imageViewModel.setImageState(ImageState.SUCCESS)
            } ?: run {
                imageFlow.url?.let { url ->
                    val placeholderImageResourceId = imageFlow.placeholderImageResource?.resourceId(imageView.context)

                    val request = ImageRequest.Builder(imageView.context)
                        .data(url)
                        .apply {
                            placeholderImageResourceId?.let { placeholder(it) }
                            transformations?.let { transformations(it) }
                        }
                        .target(
                            onStart = { placeholder ->
                                if (placeholderImageResourceId != null && placeholder != null) {
                                    placeholderScaleType?.let { imageView.scaleType = it }
                                    imageView.setImageDrawable(placeholder.asDrawable(imageView.resources))
                                } else {
                                    imageView.setImageDrawable(null)
                                }
                            },
                            onSuccess = { result ->
                                imageView.scaleType = originalScaleType
                                imageView.setImageDrawable(result.asDrawable(imageView.resources))
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
                                            transformations,
                                            originalScaleType,
                                            placeholderScaleType,
                                            cancellableManagerProvider.cancelPreviousAndCreate(),
                                            imageLoader
                                        )
                                    }
                                } ?: run {
                                    imageViewModel.setImageState(ImageState.SUCCESS)
                                }
                            },
                            onError = {
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
                                            transformations,
                                            originalScaleType,
                                            placeholderScaleType,
                                            cancellableManagerProvider.cancelPreviousAndCreate(),
                                            imageLoader
                                        )
                                    }
                                } ?: run {
                                    imageViewModel.setImageState(ImageState.ERROR)
                                }
                            }
                        )
                        .build()

                    imageLoader.enqueue(request)
                } ?: run {
                    imageFlow.placeholderImageResource?.asDrawable(imageView.context)?.let {
                        placeholderScaleType?.let { scaleType -> imageView.scaleType = scaleType }
                        imageView.setImageDrawable(it)
                    }
                }
            }
        }
    }
}
