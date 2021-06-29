package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.ToggleViewModel
import com.mirego.trikot.viewmodels.declarative.content.Content
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.PublishedProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.published
import org.reactivestreams.Publisher

@Suppress("LeakingThis")
open class ToggleViewModelImpl<C : Content>(
    cancellableManager: CancellableManager,
    defaultContent: C
) : ToggleViewModel<C>(cancellableManager) {

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

    override val propertyMapping: Map<String, PublishedProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::isOn.name] = isOnDelegate
            it[this::content.name] = contentDelegate
        }
    }
}
