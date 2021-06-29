package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.ListViewModel
import com.mirego.trikot.viewmodels.declarative.content.IdentifiableContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.PublishedProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.published
import org.reactivestreams.Publisher

@Suppress("LeakingThis")
open class ListViewModelImpl<C : IdentifiableContent>(cancellableManager: CancellableManager) :
    ListViewModel<C>(cancellableManager) {

    private val elementsDelegate: PublishedProperty<List<C>> = published(emptyList(), this)
    override var elements: List<C> by elementsDelegate

    fun bindElements(publisher: Publisher<List<C>>) {
        updatePropertyPublisher(this::elements, cancellableManager, publisher)
    }

    override val propertyMapping: Map<String, PublishedProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::elements.name] = elementsDelegate
        }
    }
}
