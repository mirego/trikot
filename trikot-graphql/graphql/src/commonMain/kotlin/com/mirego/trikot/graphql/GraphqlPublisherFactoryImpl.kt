package com.mirego.trikot.graphql

import com.mirego.trikot.http.HttpHeaderProvider
import com.mirego.trikot.streams.reactive.executable.ExecutablePublisher
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

class GraphqlPublisherFactoryImpl : GraphqlPublisherFactory {
    @ExperimentalTime
    override fun <T> create(
        request: GraphqlQuery<T>,
        httpHeaderProvider: HttpHeaderProvider,
        requestTimeout: Duration?
    ): ExecutablePublisher<T> {
        return GraphqlQueryPublisher(request, httpHeaderProvider = httpHeaderProvider, requestTimeout = requestTimeout)
    }
}
