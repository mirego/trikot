package com.mirego.trikot.datasources.flow

import com.mirego.trikot.datasources.DataState
import com.mirego.trikot.datasources.flow.extensions.withPreviousDataStateValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
class RetryableDataFlow<T>(
    scope: CoroutineScope,
    sharingStarted: SharingStarted = SharingStarted.Lazily,
    initialValue: DataState<T, Throwable> = DataState.pending(),
    private val flowBlock: () -> Flow<DataState<T, Throwable>>
) : StateFlow<DataState<T, Throwable>> {
    private val retryCount = MutableStateFlow(0)

    private val data: StateFlow<DataState<T, Throwable>> =
        retryCount.flatMapLatest { flowBlock() }
            .withPreviousDataStateValue()
            .stateIn(scope, sharingStarted, initialValue)

    fun retryIfError() {
        if (data.value.isError()) {
            retry()
        }
    }

    fun retry() {
        retryCount.value = retryCount.value + 1
    }

    override val replayCache: List<DataState<T, Throwable>>
        get() = data.replayCache

    override val value: DataState<T, Throwable>
        get() = data.value

    override suspend fun collect(collector: FlowCollector<DataState<T, Throwable>>): Nothing {
        data.collect(collector)
    }
}
