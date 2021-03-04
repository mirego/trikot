package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.LoadingViewModel
import com.mirego.trikot.viewmodels.declarative.impl.ViewModelImpl
import com.mirego.trikot.viewmodels.declarative.internal.PublishedProperty
import com.mirego.trikot.viewmodels.declarative.internal.published
import org.reactivestreams.Publisher
import kotlin.reflect.KProperty

@Suppress("LeakingThis")
open class LoadingViewModelImpl(cancellableManager: CancellableManager) : ViewModelImpl(cancellableManager), LoadingViewModel {

    private val isLoadingDelegate = published(false, this)
    override var isLoading: Boolean by isLoadingDelegate

    fun bindIsLoading(publisher: Publisher<Boolean>) {
        updatePropertyPublisher(
            this::isLoading,
            cancellableManager,
            publisher
        )
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V> publishedProperty(property: KProperty<V>): PublishedProperty<V>? =
        when (property.name) {
            this::isLoading.name -> isLoadingDelegate as PublishedProperty<V>
            else -> super.publishedProperty(property)
        }
}
