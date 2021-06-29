package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.ImageViewModel
import com.mirego.trikot.viewmodels.declarative.properties.ImageDescriptor
import com.mirego.trikot.viewmodels.declarative.properties.ImageResource
import com.mirego.trikot.viewmodels.declarative.viewmodel.ViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.PublishedProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.published
import org.reactivestreams.Publisher

@Suppress("LeakingThis")
open class ImageViewModelImpl(cancellableManager: CancellableManager) :
    ViewModelImpl(cancellableManager), ImageViewModel {

    private val imageDelegate =
        published(ImageDescriptor.Local(ImageResource.None) as ImageDescriptor, this)
    override var image: ImageDescriptor by imageDelegate

    fun bindImage(publisher: Publisher<ImageDescriptor>) {
        updatePropertyPublisher(this::image, cancellableManager, publisher)
    }

    override val propertyMapping: Map<String, PublishedProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::image.name] = imageDelegate
        }
    }
}
