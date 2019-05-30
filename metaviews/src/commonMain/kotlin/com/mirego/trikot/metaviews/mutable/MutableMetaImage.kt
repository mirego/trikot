package com.mirego.trikot.metaviews.mutable

import com.mirego.trikot.metaviews.ImageFlow
import com.mirego.trikot.metaviews.ImageHeight
import com.mirego.trikot.metaviews.ImageWidth
import com.mirego.trikot.metaviews.MetaImage
import com.mirego.trikot.metaviews.properties.Color
import com.mirego.trikot.metaviews.properties.SimpleImageFlow
import com.mirego.trikot.metaviews.resource.ImageResource
import com.mirego.trikot.streams.reactive.PublisherFactory
import org.reactivestreams.Publisher

/**
 * Block to execute to retrieve an imageFlow Publisher for a specific image size
 */
typealias ImageFlowProvider = (width: ImageWidth, height: ImageHeight) -> Publisher<ImageFlow>

open class MutableMetaImage(var imageFlowProvider: ImageFlowProvider) : MutableMetaView(), MetaImage {
    override fun imageFlow(width: ImageWidth, height: ImageHeight): Publisher<ImageFlow> {
        return imageFlowProvider(width, height)
    }
}

fun simpleImageFlowProvider(url: String? = null, imageResource: ImageResource? = null, tintColor: Color? = null): ImageFlowProvider {
    return { _, _ -> PublisherFactory.create(SimpleImageFlow(url = url, imageResource = imageResource, tintColor = tintColor)) }
}
