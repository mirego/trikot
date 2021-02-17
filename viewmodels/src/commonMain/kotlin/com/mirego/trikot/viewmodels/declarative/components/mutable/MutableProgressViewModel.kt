package com.mirego.trikot.viewmodels.declarative.components.mutable

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.ProgressViewModel
import com.mirego.trikot.viewmodels.declarative.internal.PublishedProperty
import com.mirego.trikot.viewmodels.declarative.internal.published
import com.mirego.trikot.viewmodels.declarative.mutable.MutableViewModel
import com.mirego.trikot.viewmodels.declarative.properties.ProgressDetermination
import kotlin.reflect.KProperty
import org.reactivestreams.Publisher

class MutableProgressViewModel(cancellableManager: CancellableManager) :
    MutableViewModel(cancellableManager), ProgressViewModel {

    private val determinationDelegate = published(null as ProgressDetermination?, this)
    override var determination: ProgressDetermination? by determinationDelegate

    fun bindDetermination(publisher: Publisher<ProgressDetermination?>) {
        updatePropertyPublisher(
            this::determination,
            cancellableManager,
            publisher
        )
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V> publishedProperty(property: KProperty<V>): PublishedProperty<V>? =
        when (property.name) {
            this::determination.name -> determinationDelegate as PublishedProperty<V>
            else -> super.publishedProperty(property)
        }
}
