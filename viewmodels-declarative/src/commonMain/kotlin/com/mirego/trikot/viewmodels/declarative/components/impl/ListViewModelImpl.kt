package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.ListViewModel
import com.mirego.trikot.viewmodels.declarative.impl.ViewModelImpl
import com.mirego.trikot.viewmodels.declarative.internal.PublishedProperty
import com.mirego.trikot.viewmodels.declarative.internal.published
import com.mirego.trikot.viewmodels.declarative.properties.IdentifiableContent
import org.reactivestreams.Publisher
import kotlin.reflect.KProperty

@Suppress("LeakingThis")
open class ListViewModelImpl<C : IdentifiableContent>(cancellableManager: CancellableManager) : ViewModelImpl(cancellableManager), ListViewModel<C> {

    private val elementsDelegate: PublishedProperty<List<C>> = published(emptyList(), this)
    override var elements: List<C> by elementsDelegate

    fun bindElements(publisher: Publisher<List<C>>) {
        updatePropertyPublisher(this::elements, cancellableManager, publisher)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V> publishedProperty(property: KProperty<V>): PublishedProperty<V>? =
        when (property.name) {
            this::elements.name -> elementsDelegate as PublishedProperty<V>
            else -> super.publishedProperty(property)
        }
}
