package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.VMDProgressViewModel
import com.mirego.trikot.viewmodels.declarative.properties.VMDProgressDetermination
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDPublishedProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.published
import org.reactivestreams.Publisher

@Suppress("LeakingThis")
open class VMDProgressViewModelImpl(cancellableManager: CancellableManager) :
    VMDViewModelImpl(cancellableManager), VMDProgressViewModel {

    private val determinationDelegate = published(null as VMDProgressDetermination?, this)
    override var determination: VMDProgressDetermination? by determinationDelegate

    fun bindDetermination(publisher: Publisher<VMDProgressDetermination?>) {
        updatePropertyPublisher(this::determination, cancellableManager, publisher)
    }

    override val propertyMapping: Map<String, VMDPublishedProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::determination.name] = determinationDelegate
        }
    }
}
