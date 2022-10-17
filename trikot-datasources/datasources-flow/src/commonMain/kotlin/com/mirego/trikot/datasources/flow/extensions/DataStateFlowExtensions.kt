package com.mirego.trikot.datasources.flow.extensions

import com.mirego.trikot.datasources.extensions.getFirstError
import com.mirego.trikot.datasources.extensions.isAnyDataStateError
import com.mirego.trikot.datasources.extensions.isAnyDataStatePending
import com.mirego.trikot.datasources.extensions.value
import com.mirego.trikot.datasources.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

fun <T, R, E : Throwable> Flow<DataState<out T, out E>>.mapValue(
    block: suspend (data: T) -> R
): Flow<DataState<R, E>> {
    return map { dataState ->
        when (dataState) {
            is DataState.Data -> DataState.data(block(dataState.value))
            is DataState.Pending -> DataState.Pending(dataState.value?.let { block(it) })
            is DataState.Error -> DataState.Error(dataState.error, dataState.value?.let { block(it) })
        }
    }
}

fun <T, R, E : Throwable> Flow<DataState<out T, out E>>.flatMapLatestDataState(
    getSuccessTransform: suspend (data: T) -> Flow<DataState<R, E>>
): Flow<DataState<R, E>> {
    return flatMapLatest {
        when (it) {
            is DataState.Data -> getSuccessTransform(it.value)
            is DataState.Pending -> flowOf(DataState.Pending())
            is DataState.Error -> flowOf(DataState.Error(it.error))
        }
    }
}

inline fun <reified T, R> combineData(
    flows: Iterable<Flow<DataState<T, Throwable>>>,
    crossinline transform: suspend (Array<T>) -> R
): Flow<DataState<R, Throwable>> =
    combine(flows) { a ->
        val transformInnerValue: suspend () -> R = {
            transform(a.mapNotNull { it.value() }.toTypedArray())
        }
        when {
            isAnyDataStatePending(*a) -> DataState.pending(transformInnerValue())
            isAnyDataStateError(*a) -> DataState.error(getFirstError(*a), transformInnerValue())
            else -> DataState.data(transformInnerValue())
        }
    }

fun <T1, T2, R> Flow<DataState<T1, Throwable>>.combineData(
    flow: Flow<DataState<T2, Throwable>>,
    transform: suspend (a: T1, b: T2) -> R
): Flow<DataState<R, Throwable>> =
    combine(flow) { a, b ->
        val transformInnerValue: suspend () -> R? = {
            multiLet(a.value(), b.value()) { aValue, bValue ->
                transform(aValue, bValue)
            }
        }
        when {
            isAnyDataStatePending(a, b) -> DataState.pending(transformInnerValue())
            isAnyDataStateError(a, b) -> DataState.error(getFirstError(a, b), transformInnerValue())
            else -> DataState.data(transformInnerValue()!!)
        }
    }

fun <T1, T2, T3, R> Flow<DataState<T1, Throwable>>.combineData(
    otherFlow1: Flow<DataState<T2, Throwable>>,
    otherFlow2: Flow<DataState<T3, Throwable>>,
    transform: suspend (a: T1, b: T2, c: T3) -> R
): Flow<DataState<R, Throwable>> =
    combine(
        this,
        otherFlow1,
        otherFlow2
    ) { a, b, c ->
        val transformInnerValue: suspend () -> R? = {
            multiLet(a.value(), b.value(), c.value()) { aValue, bValue, cValue ->
                transform(aValue, bValue, cValue)
            }
        }
        when {
            isAnyDataStatePending(a, b, c) -> DataState.pending(transformInnerValue())
            isAnyDataStateError(a, b, c) -> DataState.error(getFirstError(a, b, c), transformInnerValue())
            else -> DataState.data(transformInnerValue()!!)
        }
    }

fun <T1, T2, T3, T4, R> Flow<DataState<T1, Throwable>>.combineData(
    otherFlow1: Flow<DataState<T2, Throwable>>,
    otherFlow2: Flow<DataState<T3, Throwable>>,
    otherFlow3: Flow<DataState<T4, Throwable>>,
    transform: suspend (a: T1, b: T2, c: T3, d: T4) -> R
): Flow<DataState<R, Throwable>> =
    combine(
        this,
        otherFlow1,
        otherFlow2,
        otherFlow3
    ) { a, b, c, d ->
        val transformInnerValue: suspend () -> R? = {
            multiLet(a.value(), b.value(), c.value(), d.value()) { aValue, bValue, cValue, dValue ->
                transform(aValue, bValue, cValue, dValue)
            }
        }
        when {
            isAnyDataStatePending(a, b, c, d) -> DataState.pending(transformInnerValue())
            isAnyDataStateError(a, b, c, d) -> DataState.error(getFirstError(a, b, c, d), transformInnerValue())
            else -> DataState.data(transformInnerValue()!!)
        }
    }

fun <T1, T2, T3, T4, T5, R> Flow<DataState<T1, Throwable>>.combineData(
    otherFlow1: Flow<DataState<T2, Throwable>>,
    otherFlow2: Flow<DataState<T3, Throwable>>,
    otherFlow3: Flow<DataState<T4, Throwable>>,
    otherFlow4: Flow<DataState<T5, Throwable>>,
    transform: suspend (a: T1, b: T2, c: T3, d: T4, e: T5) -> R
): Flow<DataState<R, Throwable>> =
    combine(
        this,
        otherFlow1,
        otherFlow2,
        otherFlow3,
        otherFlow4
    ) { a, b, c, d, e ->
        val transformInnerValue: suspend () -> R? = {
            multiLet(a.value(), b.value(), c.value(), d.value(), e.value()) { aValue, bValue, cValue, dValue, eValue ->
                transform(aValue, bValue, cValue, dValue, eValue)
            }
        }
        when {
            isAnyDataStatePending(a, b, c, d) -> DataState.pending(transformInnerValue())
            isAnyDataStateError(a, b, c, d) -> DataState.error(getFirstError(a, b, c, d), transformInnerValue())
            else -> DataState.data(transformInnerValue()!!)
        }
    }

fun <T, E : Throwable> Flow<DataState<T, E>>.filterValue(): Flow<T> =
    transform { dataState ->
        val data = dataState.value()
        if (data != null) return@transform emit(data)
    }

fun <T> Flow<DataState<T, Throwable>>.withPreviousDataStateValue(): Flow<DataState<T, Throwable>> = flow {
    var previous: DataState<T, Throwable>? = null
    collect { current ->
        val new = when (current) {
            is DataState.Data -> current
            is DataState.Error -> if (current.value != null) current else DataState.error(current.error, value = previous?.value())
            is DataState.Pending -> if (current.value != null) current else DataState.pending(value = previous?.value())
        }
        previous = new
        emit(new)
    }
}

fun <T> Flow<T>.withPreviousValue(): Flow<Pair<T?, T>> = flow {
    var previous: T? = null
    collect { current ->
        emit(previous to current)
        previous = current
    }
}

private inline fun <T1 : Any, T2 : Any, R : Any> multiLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? =
    if (p1 != null && p2 != null) block(p1, p2) else null

private inline fun <T1 : Any, T2 : Any, T3 : Any, R : Any> multiLet(p1: T1?, p2: T2?, p3: T3?, block: (T1, T2, T3) -> R?): R? =
    if (p1 != null && p2 != null && p3 != null) block(p1, p2, p3) else null

private inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, R : Any> multiLet(p1: T1?, p2: T2?, p3: T3?, p4: T4?, block: (T1, T2, T3, T4) -> R?): R? =
    if (p1 != null && p2 != null && p3 != null && p4 != null) block(p1, p2, p3, p4) else null

private inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, R : Any> multiLet(p1: T1?, p2: T2?, p3: T3?, p4: T4?, p5: T5?, block: (T1, T2, T3, T4, T5) -> R?): R? =
    if (p1 != null && p2 != null && p3 != null && p4 != null && p5 != null) block(p1, p2, p3, p4, p5) else null
