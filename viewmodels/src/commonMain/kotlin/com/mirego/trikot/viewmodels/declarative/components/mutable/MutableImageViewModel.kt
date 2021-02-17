package com.mirego.trikot.viewmodels.declarative.components.mutable

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.ImageViewModel
import com.mirego.trikot.viewmodels.declarative.internal.PublishedProperty
import com.mirego.trikot.viewmodels.declarative.internal.published
import com.mirego.trikot.viewmodels.declarative.mutable.MutableViewModel
import com.mirego.trikot.viewmodels.declarative.properties.ImageDescriptor
import com.mirego.trikot.viewmodels.declarative.properties.ImageResource
import kotlin.reflect.KProperty
import org.reactivestreams.Publisher

class MutableImageViewModel(cancellableManager: CancellableManager) :
    MutableViewModel(cancellableManager), ImageViewModel {

    private val imageDelegate =
        published(ImageDescriptor.Local(ImageResource.None) as ImageDescriptor, this)
    override var image: ImageDescriptor by imageDelegate

    fun bindImage(publisher: Publisher<ImageDescriptor>) {
        updatePropertyPublisher(
            this::image,
            cancellableManager,
            publisher
        )
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V> publishedProperty(property: KProperty<V>): PublishedProperty<V>? =
        when (property.name) {
            this::image.name -> imageDelegate as PublishedProperty<V>
            else -> super.publishedProperty(property)
        }
}
