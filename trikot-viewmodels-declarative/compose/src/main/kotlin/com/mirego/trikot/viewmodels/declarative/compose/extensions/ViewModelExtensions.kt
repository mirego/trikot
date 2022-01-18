package com.mirego.trikot.viewmodels.declarative.compose.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.remember
import com.mirego.trikot.streams.cancellable.Cancellable
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.map
import com.mirego.trikot.streams.reactive.subscribe
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel
import org.reactivestreams.Publisher
import kotlin.reflect.KProperty

@Composable
fun <T : VMDViewModel> T.observeAsState(): State<T> {
    return propertyDidChange.map { this }.subscribeAsState(initial = this)
}

@Composable
fun <T, VM : VMDViewModel> VM.observeAsState(
    property: KProperty<T>,
    initialValue: T? = null
): State<T> {
    val initial: T = initialValue ?: property.getter.call()
    return publisherForProperty(property).subscribeAsState(initial = initial)
}

@Composable
fun <R, T : R> Publisher<T>.subscribeAsState(initial: R): State<R> =
    asState(initial) { callback ->
        val cancellableManager = CancellableManager()
        subscribe(cancellableManager) {
            callback(it)
        }
        cancellableManager
    }

@Composable
inline fun <T, S> S.asState(
    initial: T,
    crossinline subscribe: S.((T) -> Unit) -> Cancellable
): State<T> {
    val state = remember { mutableStateOf(initial, neverEqualPolicy()) }
    DisposableEffect(this) {
        val cancellable = subscribe {
            state.value = it
        }
        onDispose { cancellable.cancel() }
    }
    return state
}
