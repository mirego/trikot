package com.mirego.trikot.viewmodels.declarative.components.mutable

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.TextViewModel
import com.mirego.trikot.viewmodels.declarative.internal.PublishedProperty
import com.mirego.trikot.viewmodels.declarative.internal.published
import com.mirego.trikot.viewmodels.declarative.mutable.MutableViewModel
import kotlin.reflect.KProperty
import org.reactivestreams.Publisher

class MutableTextViewModel(cancellableManager: CancellableManager) :
    MutableViewModel(cancellableManager), TextViewModel {

    private val textDelegate = published("", this)
    override var text: String by textDelegate

    fun bindText(publisher: Publisher<String>) {
        updatePropertyPublisher(
            this::text,
            cancellableManager,
            publisher
        )
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V> publishedProperty(property: KProperty<V>): PublishedProperty<V>? =
        when (property.name) {
            this::text.name -> textDelegate as PublishedProperty<V>
            else -> super.publishedProperty(property)
        }
}
