package com.mirego.trikot.graphql

import com.mirego.trikot.http.HttpConfiguration
import com.mirego.trikot.http.HttpHeaderProvider
import com.mirego.trikot.streams.reactive.executable.ExecutablePublisher
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

interface GraphqlPublisherFactory {
    @ExperimentalTime
    fun <T> create(
        request: GraphqlQuery<T>,
        httpHeaderProvider: HttpHeaderProvider = HttpConfiguration.defaultHttpHeaderProvider,
        requestTimeout: Duration? = null
    ): ExecutablePublisher<T>
}
