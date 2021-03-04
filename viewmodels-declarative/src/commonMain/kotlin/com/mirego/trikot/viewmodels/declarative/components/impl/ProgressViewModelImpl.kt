package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.ProgressViewModel
import com.mirego.trikot.viewmodels.declarative.impl.ViewModelImpl
import com.mirego.trikot.viewmodels.declarative.internal.PublishedProperty
import com.mirego.trikot.viewmodels.declarative.internal.published
import com.mirego.trikot.viewmodels.declarative.properties.ProgressDetermination
import org.reactivestreams.Publisher
import kotlin.reflect.KProperty

@Suppress("LeakingThis")
open class ProgressViewModelImpl(cancellableManager: CancellableManager) : ViewModelImpl(cancellableManager), ProgressViewModel {

    private val determinationDelegate = published(null as ProgressDetermination?, this)
    override var determination: ProgressDetermination? by determinationDelegate

    fun bindDetermination(publisher: Publisher<ProgressDetermination?>) {
        updatePropertyPublisher(this::determination, cancellableManager, publisher)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V> publishedProperty(property: KProperty<V>): PublishedProperty<V>? =
        when (property.name) {
            this::determination.name -> determinationDelegate as PublishedProperty<V>
            else -> super.publishedProperty(property)
        }
}
