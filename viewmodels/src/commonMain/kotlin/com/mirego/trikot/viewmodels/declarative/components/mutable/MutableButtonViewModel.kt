package com.mirego.trikot.viewmodels.declarative.components.mutable

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.PublishSubjectImpl
import com.mirego.trikot.streams.reactive.first
import com.mirego.trikot.streams.reactive.observeOn
import com.mirego.trikot.streams.reactive.subscribe
import com.mirego.trikot.streams.reactive.switchMap
import com.mirego.trikot.viewmodels.declarative.components.ButtonViewModel
import com.mirego.trikot.viewmodels.declarative.internal.PublishedProperty
import com.mirego.trikot.viewmodels.declarative.internal.published
import com.mirego.trikot.viewmodels.declarative.mutable.MutableViewModel
import com.mirego.trikot.viewmodels.declarative.properties.Content
import com.mirego.trikot.viewmodels.declarative.utilities.DispatchQueues
import kotlin.reflect.KProperty
import org.reactivestreams.Publisher

class MutableButtonViewModel<C : Content>(
    cancellableManager: CancellableManager,
    defaultContent: C
) :
    MutableViewModel(cancellableManager),
    ButtonViewModel<C> {

    private val actionPublisher = PublishSubjectImpl<Unit>()
    override val action: () -> Unit = {
        actionPublisher.value = Unit
    }

    private val contentDelegate = published(defaultContent, this)
    override var content: C by contentDelegate

    fun setAction(action: () -> Unit) {
        actionPublisher
            .observeOn(DispatchQueues.uiQueue)
            .subscribe(
                cancellableManager,
                onNext = {
                    action()
                }
            )
    }

    fun <T> setAction(publisher: Publisher<T>, action: (T) -> Unit) {
        actionPublisher
            .switchMap {
                publisher.first()
            }
            .observeOn(DispatchQueues.uiQueue)
            .subscribe(
                cancellableManager,
                onNext = {
                    action(it)
                }
            )
    }

    fun bindContent(publisher: Publisher<C>) {
        updatePropertyPublisher(
            this::content,
            cancellableManager,
            publisher
        )
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V> publishedProperty(property: KProperty<V>): PublishedProperty<V>? =
        when (property.name) {
            this::content.name -> contentDelegate as PublishedProperty<V>
            else -> super.publishedProperty(property)
        }
}
