package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.ToggleViewModel
import com.mirego.trikot.viewmodels.declarative.internal.PublishedProperty
import com.mirego.trikot.viewmodels.declarative.internal.published
import com.mirego.trikot.viewmodels.declarative.properties.Content
import org.reactivestreams.Publisher
import kotlin.reflect.KProperty

@Suppress("LeakingThis")
open class ToggleViewModelImpl<C : Content>(cancellableManager: CancellableManager, defaultContent: C) : ToggleViewModel<C>(cancellableManager) {

    private val isOnDelegate = published(false, this)
    override var isOn: Boolean by isOnDelegate

    private val contentDelegate = published(defaultContent, this)
    override var content: C by contentDelegate

    override fun onValueChange(isOn: Boolean) {
        this.isOn = isOn
    }

    fun bindContent(publisher: Publisher<C>) {
        updatePropertyPublisher(this::content, cancellableManager, publisher)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V> publishedProperty(property: KProperty<V>): PublishedProperty<V>? =
        when (property.name) {
            this::isOn.name -> isOnDelegate as PublishedProperty<V>
            this::content.name -> contentDelegate as PublishedProperty<V>
            else -> super.publishedProperty(property)
        }
}
