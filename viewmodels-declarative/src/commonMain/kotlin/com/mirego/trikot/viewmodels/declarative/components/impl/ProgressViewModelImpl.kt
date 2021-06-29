package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.ProgressViewModel
import com.mirego.trikot.viewmodels.declarative.properties.ProgressDetermination
import com.mirego.trikot.viewmodels.declarative.viewmodel.ViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.PublishedProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.published
import org.reactivestreams.Publisher

@Suppress("LeakingThis")
open class ProgressViewModelImpl(cancellableManager: CancellableManager) :
    ViewModelImpl(cancellableManager), ProgressViewModel {

    private val determinationDelegate = published(null as ProgressDetermination?, this)
    override var determination: ProgressDetermination? by determinationDelegate

    fun bindDetermination(publisher: Publisher<ProgressDetermination?>) {
        updatePropertyPublisher(this::determination, cancellableManager, publisher)
    }

    override val propertyMapping: Map<String, PublishedProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::determination.name] = determinationDelegate
        }
    }
}
