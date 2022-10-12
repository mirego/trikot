package com.mirego.trikot.datasources.extensions

import com.mirego.trikot.datasources.DataState
import com.mirego.trikot.streams.reactive.filterIs
import com.mirego.trikot.streams.reactive.filterNotNull
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.streams.reactive.map
import com.mirego.trikot.streams.reactive.onErrorReturn
import com.mirego.trikot.streams.reactive.processors.MapProcessorBlock
import com.mirego.trikot.streams.reactive.startWith
import com.mirego.trikot.streams.reactive.switchMap
import org.reactivestreams.Publisher

@Suppress("USELESS_CAST", "UNCHECKED_CAST")
fun <T, E : Throwable> Publisher<T>.mapToDataState(pendingValue: T? = null): Publisher<DataState<T, E>> =
    map { DataState.Data<T, E>(it) as DataState<T, E> }
        .onErrorReturn {
            DataState.Error(it as E)
        }
        .startWith(DataState.Pending(pendingValue))

fun <T, R, E : Throwable> Publisher<DataState<T, E>>.mapData(block: MapProcessorBlock<T, R>): Publisher<DataState<R, E>> =
    map { it.mapData(block) }

@Suppress("UNCHECKED_CAST")
fun <T, R, E : Throwable> Publisher<DataState<T, E>>.switchMapDataState(
    getSuccessContinuationPublisher: (data: T) -> Publisher<DataState<R, E>>
): Publisher<DataState<R, E>> {
    return switchMap {
        when (it) {
            is DataState.Data -> getSuccessContinuationPublisher(it.value)
            is DataState.Pending -> DataState.Pending<R, E>().just()
            is DataState.Error -> DataState.Error<R, E>(it.error).just()
        } as Publisher<DataState<R, E>>
    }
}

@Suppress("UNCHECKED_CAST")
fun <T, R, E : Throwable> Publisher<DataState<T, E>>.switchMapDataState(
    getSuccessContinuationPublisher: (data: T) -> Publisher<DataState<R, E>>,
    getErrorTransform: ((error: E) -> E)? = null
): Publisher<DataState<R, E>> {
    return switchMap {
        when (it) {
            is DataState.Data -> getSuccessContinuationPublisher(it.value)
            is DataState.Pending -> DataState.Pending<R, E>().just()
            is DataState.Error -> DataState.Error<R, E>(
                getErrorTransform?.let { errorTransform ->
                    errorTransform(
                        it.error
                    )
                } ?: it.error
            ).just()
        } as Publisher<DataState<R, E>>
    }
}

@Suppress("UNCHECKED_CAST")
fun <T, R, E : Throwable> Publisher<DataState<T, E>>.mapDataState(
    getSuccessTransform: (data: T) -> DataState<R, E>
): Publisher<DataState<R, E>> {
    return map {
        when (it) {
            is DataState.Data -> getSuccessTransform(it.value)
            is DataState.Pending -> DataState.Pending<R, E>()
            is DataState.Error -> DataState.Error<R, E>(it.error)
        }
    }
}

fun <T, E : Throwable> Publisher<DataState<T, E>>.filterIsError() =
    filterIs<DataState<T, E>, DataState.Error<T, E>>()

fun <T, E : Throwable> Publisher<DataState<T, E>>.filterIsData() =
    filterIs<DataState<T, E>, DataState.Data<T, E>>()

fun <T, E : Throwable> Publisher<DataState<T, E>>.filterValue() =
    filterNotNull { it.value() }
