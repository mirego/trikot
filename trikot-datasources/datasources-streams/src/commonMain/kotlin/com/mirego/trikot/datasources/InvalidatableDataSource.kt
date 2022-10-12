package com.mirego.trikot.datasources

import com.mirego.trikot.streams.cancellable.Cancellable
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.subscribe
import org.reactivestreams.Publisher

class InvalidatableDataSource<R : DataSourceRequest, T>(
    private val masterDataSource: BaseDataSource<R, T>,
    shouldInvalidatePublisher: Publisher<Boolean>
) : DataSource<R, T> by masterDataSource, Cancellable {
    private val cancellableManager = CancellableManager()

    init {
        shouldInvalidatePublisher.subscribe(cancellableManager) {
            if (it) {
                masterDataSource.invalidate()
            }
        }
    }

    override fun cancel() {
        cancellableManager.cancel()
    }
}
