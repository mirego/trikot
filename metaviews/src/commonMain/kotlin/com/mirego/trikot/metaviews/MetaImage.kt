package com.mirego.trikot.metaviews

import com.mirego.trikot.metaviews.properties.Color
import com.mirego.trikot.metaviews.resource.ImageResource
import org.reactivestreams.Publisher

@Suppress("EXPERIMENTAL_FEATURE_WARNING")
inline class ImageWidth(val value: Int)
@Suppress("EXPERIMENTAL_FEATURE_WARNING")
inline class ImageHeight(val value: Int)

interface MetaImage : MetaView {
    fun imageFlow(width: ImageWidth, height: ImageHeight): Publisher<ImageFlow>
}

interface ImageFlow {
    /**
     * Image resource to display as placeholder/static image
     */
    val imageResource: ImageResource?
    /**
     * Tint color to apply to the imageResource
     */
    val tintColor: Color?
    /**
     * Accessibility text
     */
    val accessibilityText: String?
    /**
     * URL to download the image from. When downloaded, it will replace the imageResource if any
     */
    val URL: String?
    /**
     * Next image flow to use when an URL is provided and the image is successfully displayed. Usefull when a low
     * quality image needs to be displayed before a full quality image.
     */
    val onSuccess: Publisher<ImageFlow>?
    /**
     * Next image flow to use when an URL is provided and the image could not be downloaded/displayed. Usefull when a low
     * quality image needs to be displayed before a full quality image.
     */
    val onError: Publisher<ImageFlow>?
}
