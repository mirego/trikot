package com.mirego.trikot.viewmodels.declarative.compose.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.remember
import com.mirego.trikot.streams.cancellable.Cancellable
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.filter
import com.mirego.trikot.streams.reactive.first
import com.mirego.trikot.streams.reactive.map
import com.mirego.trikot.streams.reactive.merge
import com.mirego.trikot.streams.reactive.subscribe
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDPropertyChange
import org.reactivestreams.Publisher
import kotlin.reflect.KProperty

data class VMDAnimatedPropertyChange<T, V>(val value: T, val propertyChange: VMDPropertyChange<V>)

@Composable
fun <T : VMDViewModel> T.observeAsState(excludedProperties: List<KProperty<*>> = emptyList()): State<T> {
    return propertyDidChange
        .filter { propertyChange -> !excludedProperties.map { it.name }.contains(propertyChange.property.name) }
        .map { this }
        .subscribeAsState(initial = this, key = this)
}

@Composable
fun <T, VM : VMDViewModel> VM.observeAsState(
    property: KProperty<T>,
    initialValue: T? = null
): State<T> {
    val initial: T = initialValue ?: property.getter.call()
    return publisherForProperty(property).subscribeAsState(initial = initial, key = this)
}

@Suppress("UNCHECKED_CAST")
@Composable
fun <VM : VMDViewModel, T> VM.observeAnimatedPropertyAsState(
    property: KProperty<T>,
    initialValue: T? = null
): State<VMDAnimatedPropertyChange<T, T>> {
    val initial: T = (initialValue ?: property.getter.call())
    val initialPropertyChange = VMDAnimatedPropertyChange(initial, VMDPropertyChange(property = property, oldValue = initial, newValue = initial))

    val propertyPublisher = publisherForProperty(property)
        .first()
        .map {
            VMDAnimatedPropertyChange(it, VMDPropertyChange(property = property, oldValue = it, newValue = it))
        }

    val propertyChangePublisher =  propertyDidChange
        .filter {
            it.property.name == property.name
        }
        .map {
            VMDAnimatedPropertyChange(value = it.newValue as T, propertyChange = it as VMDPropertyChange<T>)
        }

    return propertyPublisher.merge(propertyChangePublisher)
        .subscribeAsState(initial = initialPropertyChange, key = this)
}

@Suppress("UNCHECKED_CAST")
@Composable
fun <VM : VMDViewModel, T, V> VM.observeAnimatedPropertyAsState(
    property: KProperty<T>,
    initialValue: T? = null,
    transform: (T) -> V
): State<VMDAnimatedPropertyChange<V, T>> {
    val initial: T = (initialValue ?: property.getter.call())
    val initialPropertyChange = VMDAnimatedPropertyChange(transform(initial), VMDPropertyChange(property = property, oldValue = initial, newValue = initial))

    val propertyPublisher = publisherForProperty(property)
        .first()
        .map { VMDAnimatedPropertyChange(value = transform(it), VMDPropertyChange(property = property, oldValue = it, newValue = it)) }

    val propertyChangePublisher =  propertyDidChange
        .filter { it.property.name == property.name }
        .map { VMDAnimatedPropertyChange(value = transform(it.newValue as T), propertyChange = it as VMDPropertyChange<T>) }

    return propertyPublisher.merge(propertyChangePublisher)
        .subscribeAsState(initial = initialPropertyChange, key = this)
}

@Composable
fun <T> Publisher<T>.subscribeAsState(initial: T, key: Any? = initial): State<T> =
    asState(initial, key) { callback ->
        val cancellableManager = CancellableManager()
        subscribe(cancellableManager) {
            callback(it)
        }
        cancellableManager
    }

@Composable
inline fun <T, S> S.asState(
    initial: T,
    key: Any? = null,
    crossinline subscribe: S.((T) -> Unit) -> Cancellable
): State<T> {
    val state = remember(key) { mutableStateOf(initial, neverEqualPolicy()) }
    DisposableEffect(this) {
        val cancellable = subscribe {
            state.value = it
        }
        onDispose { cancellable.cancel() }
    }
    return state
}
