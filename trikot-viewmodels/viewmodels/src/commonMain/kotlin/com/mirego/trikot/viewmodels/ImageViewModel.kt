package com.mirego.trikot.viewmodels

import com.mirego.trikot.viewmodels.properties.Color
import com.mirego.trikot.viewmodels.properties.ImageState
import com.mirego.trikot.viewmodels.resource.ImageResource
import org.reactivestreams.Publisher

data class ImageWidth(val value: Int)
data class ImageHeight(val value: Int)

interface ImageViewModel : ViewModel {
    fun imageFlow(width: ImageWidth, height: ImageHeight): Publisher<ImageFlow>
    val imageState: Publisher<ImageState>

    fun setImageState(imageState: ImageState)
}

interface ImageFlow {
    /**
     * Image resource to display
     */
    val imageResource: ImageResource?

    /**
     * Image resource to display as placeholder
     */
    val placeholderImageResource: ImageResource?

    /**
     * Tint color to apply to the imageResource
     */
    val tintColor: Color?

    /**
     * Accessibility text
     */
    val accessibilityText: String?

    /**
     * Url to download the image from. When downloaded, it will replace the imageResource if any
     */
    val url: String?

    /**
     * Next image flow to use when an url is provided and the image is successfully displayed. Useful when a low
     * quality image needs to be displayed before a full quality image.
     */
    val onSuccess: Publisher<ImageFlow>?

    /**
     * Next image flow to use when an url is provided and the image could not be downloaded/displayed. Useful when a low
     * quality image needs to be displayed before a full quality image.
     */
    val onError: Publisher<ImageFlow>?
}
