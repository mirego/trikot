package com.mirego.trikot.viewmodels.declarative.components.mutable

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.ListViewModel
import com.mirego.trikot.viewmodels.declarative.internal.PublishedProperty
import com.mirego.trikot.viewmodels.declarative.internal.published
import com.mirego.trikot.viewmodels.declarative.mutable.MutableViewModel
import com.mirego.trikot.viewmodels.declarative.properties.IdentifiableContent
import kotlin.reflect.KProperty
import org.reactivestreams.Publisher

class MutableListViewModel<C : IdentifiableContent>(cancellableManager: CancellableManager) :
    MutableViewModel(cancellableManager), ListViewModel<C> {

    private val elementsDelegate: PublishedProperty<List<C>> = published(emptyList(), this)
    override var elements: List<C> by elementsDelegate

    fun bindElements(publisher: Publisher<List<C>>) {
        updatePropertyPublisher(
            this::elements,
            cancellableManager,
            publisher
        )
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V> publishedProperty(property: KProperty<V>): PublishedProperty<V>? =
        when (property.name) {
            this::elements.name -> elementsDelegate as PublishedProperty<V>
            else -> super.publishedProperty(property)
        }
}
