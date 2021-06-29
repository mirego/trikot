package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.LoadingViewModel
import com.mirego.trikot.viewmodels.declarative.viewmodel.ViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.PublishedProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.published
import org.reactivestreams.Publisher

@Suppress("LeakingThis")
open class LoadingViewModelImpl(cancellableManager: CancellableManager) :
    ViewModelImpl(cancellableManager), LoadingViewModel {

    private val isLoadingDelegate = published(false, this)
    override var isLoading: Boolean by isLoadingDelegate

    fun bindIsLoading(publisher: Publisher<Boolean>) {
        updatePropertyPublisher(
            this::isLoading,
            cancellableManager,
            publisher
        )
    }

    override val propertyMapping: Map<String, PublishedProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::isLoading.name] = isLoadingDelegate
        }
    }
}
