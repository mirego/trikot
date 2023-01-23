package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.VMDImageViewModel
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageDescriptor
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageResource
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDPublishedProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.published
import org.reactivestreams.Publisher

@Suppress("LeakingThis")
open class VMDImageViewModelImpl(cancellableManager: CancellableManager) :
    VMDViewModelImpl(cancellableManager), VMDImageViewModel {

    private val imageDelegate =
        published(VMDImageDescriptor.Local(VMDImageResource.None) as VMDImageDescriptor, this)
    override var image: VMDImageDescriptor by imageDelegate

    private val contentDescriptionDelegate: VMDPublishedProperty<String?> =
        published(null, this)
    override var contentDescription: String? by contentDescriptionDelegate

    fun bindImage(publisher: Publisher<VMDImageDescriptor>) {
        updatePropertyPublisher(this::image, cancellableManager, publisher)
    }

    fun bindContentDescription(publisher: Publisher<String?>) {
        updatePropertyPublisher(this::contentDescription, cancellableManager, publisher)
    }

    override val propertyMapping: Map<String, VMDPublishedProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::image.name] = imageDelegate
            it[this::contentDescription.name] = contentDescriptionDelegate
        }
    }
}
