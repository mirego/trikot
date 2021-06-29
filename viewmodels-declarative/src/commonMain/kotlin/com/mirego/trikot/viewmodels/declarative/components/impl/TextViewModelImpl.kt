package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.TextViewModel
import com.mirego.trikot.viewmodels.declarative.viewmodel.ViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.PublishedProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.published
import org.reactivestreams.Publisher

@Suppress("LeakingThis")
open class TextViewModelImpl(cancellableManager: CancellableManager) :
    ViewModelImpl(cancellableManager), TextViewModel {

    private val textDelegate = published("", this)
    override var text: String by textDelegate

    fun bindText(publisher: Publisher<String>) {
        updatePropertyPublisher(this::text, cancellableManager, publisher)
    }

    override val propertyMapping: Map<String, PublishedProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::text.name] = textDelegate
        }
    }
}
