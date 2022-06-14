package com.mirego.trikot.viewmodels.declarative.compose.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.remember
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDPropertyChange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty

data class VMDAnimatedPropertyChange<T, V>(val value: T, val propertyChange: VMDPropertyChange<V>)

@Composable
fun <T : VMDViewModel> T.observeAsState(excludedProperties: List<KProperty<*>> = emptyList()): State<T> {
    return remember(this) {
        propertyDidChange
            .filter { propertyChange -> !excludedProperties.map { it.name }.contains(propertyChange.property.name) }
            .map { this }
    }.subscribeAsState(initial = this, key = this)
}

@Composable
fun <T, VM : VMDViewModel> VM.observeAsState(
    property: KProperty<T>,
    initialValue: T? = null
): State<T> {
    val initial: T = initialValue ?: property.getter.call()
    return flowForProperty(property).subscribeAsState(initial = initial, key = this)
}

@Suppress("UNCHECKED_CAST")
@Composable
fun <VM : VMDViewModel, T> VM.observeAnimatedPropertyAsState(
    property: KProperty<T>,
    initialValue: T? = null
): State<VMDAnimatedPropertyChange<T, T>> {
    val initial: T = (initialValue ?: property.getter.call()) as T
    val initialPropertyChange = VMDAnimatedPropertyChange(initial, VMDPropertyChange(property = property, oldValue = initial, newValue = initial))

    val propertyFlow = remember {
        flowForProperty(property)
            .map { VMDAnimatedPropertyChange(it, VMDPropertyChange(property = property, oldValue = it, newValue = it)) }
    }

    val propertyChangeFlow =  remember {
        propertyDidChange
            .filter { it.property.name == property.name }
            .map { VMDAnimatedPropertyChange(value = it.newValue as T, propertyChange = it as VMDPropertyChange<T>) }
    }

    return merge(propertyFlow, propertyChangeFlow)
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

    val propertyFlow = remember {
        flowForProperty(property)
            .map { VMDAnimatedPropertyChange(value = transform(it), VMDPropertyChange(property = property, oldValue = it, newValue = it)) }
    }

    val propertyChangeFlow =  remember {
        propertyDidChange
            .filter { it.property.name == property.name }
            .map { VMDAnimatedPropertyChange(value = transform(it.newValue as T), propertyChange = it as VMDPropertyChange<T>) }
    }

    return merge(propertyFlow, propertyChangeFlow)
        .subscribeAsState(initial = initialPropertyChange, key = this)
}

@Composable
fun <T> Flow<T>.subscribeAsState(initial: T, key: Any? = initial): State<T> =
    asState(initial, key) { callback ->
        val coroutineScope = MainScope()
        coroutineScope.launch { collect { callback(it) } }
        coroutineScope
    }

@Composable
inline fun <T, S> S.asState(
    initial: T,
    key: Any? = null,
    crossinline subscribe: S.((T) -> Unit) -> CoroutineScope
): State<T> {
    val state = remember(key) { mutableStateOf(initial, neverEqualPolicy()) }
    DisposableEffect(this) {
        val coroutineScope = subscribe {
            state.value = it
        }
        onDispose { coroutineScope.cancel() }
    }
    return state
}
