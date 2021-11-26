package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.VMDLoadingViewModel
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDPublishedProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.published
import org.reactivestreams.Publisher

@Suppress("LeakingThis")
open class VMDLoadingViewModelImpl(cancellableManager: CancellableManager) :
    VMDViewModelImpl(cancellableManager), VMDLoadingViewModel {

    private val isLoadingDelegate = published(false, this)
    override var isLoading: Boolean by isLoadingDelegate

    fun bindIsLoading(publisher: Publisher<Boolean>) {
        updatePropertyPublisher(
            this::isLoading,
            cancellableManager,
            publisher
        )
    }

    override val propertyMapping: Map<String, VMDPublishedProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::isLoading.name] = isLoadingDelegate
        }
    }
}
