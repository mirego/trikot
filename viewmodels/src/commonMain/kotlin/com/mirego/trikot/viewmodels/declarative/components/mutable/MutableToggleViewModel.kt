package com.mirego.trikot.viewmodels.declarative.components.mutable

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.ToggleViewModel
import com.mirego.trikot.viewmodels.declarative.internal.PublishedProperty
import com.mirego.trikot.viewmodels.declarative.internal.published
import com.mirego.trikot.viewmodels.declarative.mutable.MutableViewModel
import kotlin.reflect.KProperty

class MutableToggleViewModel(cancellableManager: CancellableManager) :
    MutableViewModel(cancellableManager), ToggleViewModel {

    private val isOnDelegate = published(false, this)
    override var isOn: Boolean by isOnDelegate

    override fun onValueChange(isOn: Boolean) {
        this.isOn = isOn
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V> publishedProperty(property: KProperty<V>): PublishedProperty<V>? =
        when (property.name) {
            this::isOn.name -> isOnDelegate as PublishedProperty<V>
            else -> super.publishedProperty(property)
        }
}
