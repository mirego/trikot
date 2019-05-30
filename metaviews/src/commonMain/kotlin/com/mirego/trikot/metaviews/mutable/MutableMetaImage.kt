package com.mirego.trikot.metaviews.mutable

import com.mirego.trikot.metaviews.ImageHeight
import com.mirego.trikot.metaviews.ImageWidth
import com.mirego.trikot.metaviews.MetaImage
import com.mirego.trikot.metaviews.factory.PropertyFactory
import com.mirego.trikot.metaviews.properties.Color
import com.mirego.trikot.metaviews.resource.ImageResource
import com.mirego.trikot.streams.reactive.PublisherFactory
import org.reactivestreams.Publisher

open class MutableMetaImage(
    /**
     * Constructor property
     *
     * Processor used to convert the url when the platform sets the size
     * Could come really handy when we need to fetch a small image on a list and a large
     * image on a detail view
     */
    var imageUrlProcessor: ImageURLProcessor = DefaultImageURLProcessor()
) : MutableMetaView(), MetaImage {

    /**
     * Online URL of the image to fetch according to the view Size
     */
    override fun URL(width: ImageWidth, height: ImageHeight): Publisher<String?> {
        return imageUrlProcessor.URLForSizeSize(width, height)
    }

    /**
     * Base url given to the processor. DefaultURLProcessor will dispatch this value.
     */
    fun setUrl(url: String?) { imageUrlProcessor.imageURL = url }

    override val imageResource = PropertyFactory.create(ImageResource.None)

    override val tintColor = PropertyFactory.create(Color.None)
}

/**
 * Image processor
 */
interface ImageURLProcessor {
    var imageURL: String?
    fun URLForSizeSize(width: ImageWidth, height: ImageHeight): Publisher<String?>
}

/**
 * Default image processor that publish the url received without transformation
 */
open class DefaultImageURLProcessor : ImageURLProcessor {
    override var imageURL: String? = null
        set(value) {
            field = value
            imageUrlPublisher.value = value
        }

    protected var imageUrlPublisher = PublisherFactory.create<String?>()

    override fun URLForSizeSize(width: ImageWidth, height: ImageHeight): Publisher<String?> {
        return imageUrlPublisher
    }
}
