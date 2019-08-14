package com.mirego.trikot.http

import com.mirego.trikot.http.connectivity.ConnectivityState
import com.mirego.trikot.streams.concurrent.AtomicReference
import com.mirego.trikot.streams.concurrent.dispatchQueue.DispatchQueue
import com.mirego.trikot.streams.concurrent.dispatchQueue.OperationDispatchQueue
import com.mirego.trikot.streams.concurrent.freeze
import com.mirego.trikot.streams.reactive.PublisherFactory
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import org.reactivestreams.Publisher
import com.mirego.trikot.http.requestFactory.KtorHttpRequestFactory
import com.mirego.trikot.http.header.DefaultHttpHeaderProvider

object HttpConfiguration {
    private val internalHttpRequestFactory = AtomicReference<HttpRequestFactory>(KtorHttpRequestFactory())
    private val internalNetworkDispatchQueue = AtomicReference<DispatchQueue>(OperationDispatchQueue())
    private val internalDefaultHeaderProvider = AtomicReference<HttpHeaderProvider>(DefaultHttpHeaderProvider())
    private val internalConnectivityStatePublisher = AtomicReference<Publisher<ConnectivityState>>(PublisherFactory.create(
        ConnectivityState.WIFI))
    private val internalBaseUrl = AtomicReference("")

    var httpRequestFactory: HttpRequestFactory
        get() {
            return internalHttpRequestFactory.value
        }
        set(value) {
            internalHttpRequestFactory.setOrThrow(internalHttpRequestFactory.value, value)
        }

    var networkDispatchQueue: DispatchQueue
        get() {
            return internalNetworkDispatchQueue.value
        }
        set(value) {
            internalNetworkDispatchQueue.setOrThrow(internalNetworkDispatchQueue.value, value)
        }

    var defaultHttpHeaderProvider: HttpHeaderProvider
        get() {
            return internalDefaultHeaderProvider.value
        }
        set(value) {
            internalDefaultHeaderProvider.setOrThrow(internalDefaultHeaderProvider.value, value)
        }

    var connectivityPublisher: Publisher<ConnectivityState>
        get() {
            return internalConnectivityStatePublisher.value
        }
        set(value) {
            internalConnectivityStatePublisher.setOrThrow(internalConnectivityStatePublisher.value, value)
        }

    var baseUrl: String
        get() {
            return internalBaseUrl.value
        }
        set(value) {
            internalBaseUrl.setOrThrow(internalBaseUrl.value, value)
        }

    @UseExperimental(UnstableDefault::class)
    val json = freeze(Json.nonstrict)
}
