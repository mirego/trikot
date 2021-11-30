package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.VMDListViewModel
import com.mirego.trikot.viewmodels.declarative.content.VMDIdentifiableContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDPublishedProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.published
import org.reactivestreams.Publisher

@Suppress("LeakingThis")
open class VMDListViewModelImpl<E : VMDIdentifiableContent>(cancellableManager: CancellableManager) :
    VMDListViewModel<E>(cancellableManager) {

    private val elementsDelegate: VMDPublishedProperty<List<E>> = published(emptyList(), this)
    override var elements: List<E> by elementsDelegate

    fun bindElements(publisher: Publisher<List<E>>) {
        updatePropertyPublisher(this::elements, cancellableManager, publisher)
    }

    override val propertyMapping: Map<String, VMDPublishedProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::elements.name] = elementsDelegate
        }
    }
}
