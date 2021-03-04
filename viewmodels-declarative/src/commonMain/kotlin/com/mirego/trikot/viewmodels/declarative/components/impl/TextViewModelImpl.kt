package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.TextViewModel
import com.mirego.trikot.viewmodels.declarative.impl.ViewModelImpl
import com.mirego.trikot.viewmodels.declarative.internal.PublishedProperty
import com.mirego.trikot.viewmodels.declarative.internal.published
import org.reactivestreams.Publisher
import kotlin.reflect.KProperty

@Suppress("LeakingThis")
open class TextViewModelImpl(cancellableManager: CancellableManager) : ViewModelImpl(cancellableManager), TextViewModel {

    private val textDelegate = published("", this)
    override var text: String by textDelegate

    fun bindText(publisher: Publisher<String>) {
        updatePropertyPublisher(this::text, cancellableManager, publisher)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V> publishedProperty(property: KProperty<V>): PublishedProperty<V>? =
        when (property.name) {
            this::text.name -> textDelegate as PublishedProperty<V>
            else -> super.publishedProperty(property)
        }
}
