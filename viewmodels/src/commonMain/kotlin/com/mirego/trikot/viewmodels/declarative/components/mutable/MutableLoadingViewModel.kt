package com.mirego.trikot.viewmodels.declarative.components.mutable

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.LoadingViewModel
import com.mirego.trikot.viewmodels.declarative.internal.PublishedProperty
import com.mirego.trikot.viewmodels.declarative.internal.published
import com.mirego.trikot.viewmodels.declarative.mutable.MutableViewModel
import kotlin.reflect.KProperty
import org.reactivestreams.Publisher

class MutableLoadingViewModel(cancellableManager: CancellableManager) :
    MutableViewModel(cancellableManager), LoadingViewModel {

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
