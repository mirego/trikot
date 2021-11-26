package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.VMDControlViewModel
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDPublishedProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.published
import org.reactivestreams.Publisher

@Suppress("LeakingThis")
open class VMDControlViewModelImpl(cancellableManager: CancellableManager) : VMDControlViewModel,
    VMDViewModelImpl(cancellableManager) {

    private val enabledDelegate = published(true, this)
    override var enabled: Boolean by enabledDelegate

    fun bindEnabled(publisher: Publisher<Boolean>) {
        updatePropertyPublisher(this::enabled, cancellableManager, publisher)
    }

    override val propertyMapping: Map<String, VMDPublishedProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::enabled.name] = enabledDelegate
        }
    }
}
