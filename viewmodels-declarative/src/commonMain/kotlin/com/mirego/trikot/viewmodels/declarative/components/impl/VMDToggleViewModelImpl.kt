package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.VMDToggleViewModel
import com.mirego.trikot.viewmodels.declarative.content.VMDContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDPublishedProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.published
import org.reactivestreams.Publisher

@Suppress("LeakingThis")
open class VMDToggleViewModelImpl<C : VMDContent>(
    cancellableManager: CancellableManager,
    defaultContent: C
) : VMDToggleViewModel<C>(cancellableManager) {

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

    override val propertyMapping: Map<String, VMDPublishedProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::isOn.name] = isOnDelegate
            it[this::content.name] = contentDelegate
        }
    }
}
