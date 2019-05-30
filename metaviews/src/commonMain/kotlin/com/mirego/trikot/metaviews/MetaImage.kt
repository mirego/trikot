package com.mirego.trikot.metaviews

import com.mirego.trikot.metaviews.properties.Color
import com.mirego.trikot.metaviews.resource.ImageResource
import org.reactivestreams.Publisher

@Suppress("EXPERIMENTAL_FEATURE_WARNING")
inline class ImageWidth(val value: Int)
@Suppress("EXPERIMENTAL_FEATURE_WARNING")
inline class ImageHeight(val value: Int)

interface MetaImage : MetaView {
    /**
     * Online URL of the image to fetch according to the view Size
     */
    fun URL(width: ImageWidth, height: ImageHeight): Publisher<String?>

    /**
     * Image resource to display in the image
     */
    val imageResource: Publisher<ImageResource>

    /**
     * Tint color to apply to the image resource
     */
    val tintColor: Publisher<Color>
}
