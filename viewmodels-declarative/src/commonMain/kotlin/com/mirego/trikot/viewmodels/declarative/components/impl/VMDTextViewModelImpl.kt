package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDPublishedProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.published
import org.reactivestreams.Publisher

@Suppress("LeakingThis")
open class VMDTextViewModelImpl(cancellableManager: CancellableManager) :
    VMDViewModelImpl(cancellableManager), VMDTextViewModel {

    private val textDelegate = published("", this)
    override var text: String by textDelegate

    fun bindText(publisher: Publisher<String>) {
        updatePropertyPublisher(this::text, cancellableManager, publisher)
    }

    override val propertyMapping: Map<String, VMDPublishedProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::text.name] = textDelegate
        }
    }
}
