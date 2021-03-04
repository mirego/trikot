package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.ControlViewModel
import com.mirego.trikot.viewmodels.declarative.impl.ViewModelImpl
import com.mirego.trikot.viewmodels.declarative.internal.PublishedProperty
import com.mirego.trikot.viewmodels.declarative.internal.published
import org.reactivestreams.Publisher
import kotlin.reflect.KProperty

@Suppress("LeakingThis")
open class ControlViewModelImpl(cancellableManager: CancellableManager) : ControlViewModel, ViewModelImpl(cancellableManager) {

    private val enabledDelegate = published(true, this)
    override var enabled: Boolean by enabledDelegate

    fun bindEnabled(publisher: Publisher<Boolean>) {
        updatePropertyPublisher(this::enabled, cancellableManager, publisher)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V> publishedProperty(property: KProperty<V>): PublishedProperty<V>? =
        when (property.name) {
            this::enabled.name -> enabledDelegate as PublishedProperty<V>
            else -> super.publishedProperty(property)
        }
}
