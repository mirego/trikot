package com.mirego.trikot.viewmodels.declarative.compose.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDPropertyChange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0
import kotlin.reflect.KProperty1

data class VMDAnimatedPropertyChange<T, V>(val value: T, val propertyChange: VMDPropertyChange<V>)

@Composable
fun <T : VMDViewModel> T.observeAsState(excludedProperties: List<KProperty<*>> = emptyList()): State<T> {
    return remember(this, excludedProperties) {
        propertyDidChange
            .filter { propertyChange -> !excludedProperties.map { it.name }.contains(propertyChange.property.name) }
            .map {
                this
            }
    }.subscribeAsState(initial = this, key = this)
}

@Composable
fun <T : R, R> Flow<T>.collectAsStateLifecycleAware(
    initial: R,
    context: CoroutineContext = EmptyCoroutineContext
): State<R> {
    val lifecycleAwareFlow = rememberFlow(flow = this)
    return lifecycleAwareFlow.collectAsState(initial = initial, context = context)
}

@Composable
fun <T> rememberFlow(
    flow: Flow<T>,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
): Flow<T> {
    return remember(key1 = flow, key2 = lifecycleOwner) { flow.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED) }
}

@Composable
fun <T, VM : VMDViewModel> VM.observeAsState(
    property: KProperty<T>,
    initialValue: T? = null
): State<T> {
    val initial: T = initialValue ?: property.callGetter(this)
    return flowForProperty(property).subscribeAsState(initial = initial, key = this)
}

@Suppress("UNCHECKED_CAST")
@Composable
fun <VM : VMDViewModel, T> VM.observeAnimatedPropertyAsState(
    property: KProperty<T>,
    initialValue: T? = null
): State<VMDAnimatedPropertyChange<T, T>> {
    val initial: T = (initialValue ?: property.callGetter(this))
    val initialPropertyChange = VMDAnimatedPropertyChange(initial, VMDPropertyChange(property = property, oldValue = initial, newValue = initial))

    return remember(this, property) {
        val propertyFlow = flowForProperty(property)
            .map { VMDAnimatedPropertyChange(it, VMDPropertyChange(property = property, oldValue = it, newValue = it)) }

        val propertyChangeFlow = propertyDidChange
            .filter { it.property.name == property.name }
            .map { VMDAnimatedPropertyChange(value = it.newValue as T, propertyChange = it as VMDPropertyChange<T>) }

        merge(propertyFlow, propertyChangeFlow)
    }.subscribeAsState(initial = initialPropertyChange, key = this)
}

@Suppress("UNCHECKED_CAST")
@Composable
fun <VM : VMDViewModel, T, V> VM.observeAnimatedPropertyAsState(
    property: KProperty<T>,
    initialValue: T? = null,
    transform: (T) -> V
): State<VMDAnimatedPropertyChange<V, T>> {
    val initial: T = (initialValue ?: property.callGetter(this))
    val initialPropertyChange = VMDAnimatedPropertyChange(transform(initial), VMDPropertyChange(property = property, oldValue = initial, newValue = initial))
    return remember(this, property) {
        val propertyFlow = flowForProperty(property)
            .map {
                VMDAnimatedPropertyChange(value = transform(it), VMDPropertyChange(property = property, oldValue = it, newValue = it))
            }

        val propertyChangeFlow = propertyDidChange
            .filter { it.property.name == property.name }
            .map { VMDAnimatedPropertyChange(value = transform(it.newValue as T), propertyChange = it as VMDPropertyChange<T>) }

        merge(propertyFlow, propertyChangeFlow)
    }.subscribeAsState(initial = initialPropertyChange, key = this)
}

@Composable
fun <T> Flow<T>.subscribeAsState(initial: T, key: Any? = initial): State<T> =
    asState(initial, key) { callback ->
        val coroutineScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())
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

@Suppress("UNCHECKED_CAST")
private fun <T, V> KProperty<V>.callGetter(receiver: T): V =
    when (this) {
        is KProperty0<V> -> get()
        is KProperty1<*, *> -> (this as KProperty1<T, V>).get(receiver)
        else -> throw IllegalArgumentException("Unsupported property type: $this")
    }
