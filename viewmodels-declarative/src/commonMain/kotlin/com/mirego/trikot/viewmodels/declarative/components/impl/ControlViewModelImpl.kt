package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.ControlViewModel
import com.mirego.trikot.viewmodels.declarative.viewmodel.ViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.PublishedProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.published
import org.reactivestreams.Publisher

@Suppress("LeakingThis")
open class ControlViewModelImpl(cancellableManager: CancellableManager) : ControlViewModel,
    ViewModelImpl(cancellableManager) {

    private val enabledDelegate = published(true, this)
    override var enabled: Boolean by enabledDelegate

    fun bindEnabled(publisher: Publisher<Boolean>) {
        updatePropertyPublisher(this::enabled, cancellableManager, publisher)
    }

    override val propertyMapping: Map<String, PublishedProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::enabled.name] = enabledDelegate
        }
    }
}
