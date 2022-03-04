package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.foundation.concurrent.atomic
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.first
import com.mirego.trikot.streams.reactive.observeOn
import com.mirego.trikot.streams.reactive.subscribe
import com.mirego.trikot.viewmodels.declarative.components.VMDButtonViewModel
import com.mirego.trikot.viewmodels.declarative.content.VMDContent
import com.mirego.trikot.viewmodels.declarative.utilities.VMDDispatchQueues
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDPublishedProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.published
import org.reactivestreams.Publisher

@Suppress("LeakingThis")
open class VMDButtonViewModelImpl<C : VMDContent>(
    cancellableManager: CancellableManager,
    defaultContent: C
) : VMDButtonViewModel<C>(cancellableManager) {

    override var actionBlock: () -> Unit by atomic {}

    private val contentDelegate = published(defaultContent, this)
    override var content: C by contentDelegate

    fun setAction(action: () -> Unit) {
        actionBlock = {
            VMDDispatchQueues.uiQueue.dispatch(action)
        }
    }

    fun <T> setAction(publisher: Publisher<T>, action: (T) -> Unit) {
        actionBlock = {
            publisher
                .first()
                .observeOn(VMDDispatchQueues.uiQueue)
                .subscribe(cancellableManager, onNext = {
                    action(it)
                })
        }
    }

    fun bindContent(publisher: Publisher<C>) {
        updatePropertyPublisher(this::content, cancellableManager, publisher)
    }

    override val propertyMapping: Map<String, VMDPublishedProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::content.name] = contentDelegate
        }
    }
}
