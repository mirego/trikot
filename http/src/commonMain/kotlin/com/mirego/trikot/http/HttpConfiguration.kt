package com.mirego.trikot.http

import com.mirego.trikot.foundation.concurrent.AtomicReference
import com.mirego.trikot.foundation.concurrent.dispatchQueue.DispatchQueue
import com.mirego.trikot.foundation.concurrent.dispatchQueue.OperationDispatchQueue
import com.mirego.trikot.http.connectivity.ConnectivityState
import com.mirego.trikot.http.header.DefaultHttpHeaderProvider
import com.mirego.trikot.http.requestFactory.EmptyHttpRequestFactory
import com.mirego.trikot.streams.reactive.Publishers
import kotlinx.serialization.json.Json
import org.reactivestreams.Publisher

object HttpConfiguration {
    private val internalHttpRequestFactory =
        AtomicReference<HttpRequestFactory>(EmptyHttpRequestFactory())
    private val internalNetworkDispatchQueue =
        AtomicReference<DispatchQueue>(OperationDispatchQueue())
    private val internalDefaultHeaderProvider =
        AtomicReference<HttpHeaderProvider>(DefaultHttpHeaderProvider())
    private val internalConnectivityStatePublisher =
        AtomicReference<Publisher<ConnectivityState>>(Publishers.behaviorSubject())
    private val internalBaseUrl = AtomicReference("")
    private val internalJson =
        AtomicReference(defaultJsonConfiguration())

    private fun defaultJsonConfiguration(): Json {
        return Json {
            isLenient = true
            ignoreUnknownKeys = true
            allowSpecialFloatingPointValues = true
            useArrayPolymorphism = true
        }
    }

    /**
     * Shared HTTPRequestFactory
     */
    var httpRequestFactory: HttpRequestFactory
        get() {
            return internalHttpRequestFactory.value
        }
        set(value) {
            internalHttpRequestFactory.setOrThrow(internalHttpRequestFactory.value, value)
        }

    /**
     * DispatchQueue where the request will be launched
     */
    var networkDispatchQueue: DispatchQueue
        get() {
            return internalNetworkDispatchQueue.value
        }
        set(value) {
            internalNetworkDispatchQueue.setOrThrow(internalNetworkDispatchQueue.value, value)
        }

    /**
     * Default headerProvider used by HttpRequestPublisher
     */
    var defaultHttpHeaderProvider: HttpHeaderProvider
        get() {
            return internalDefaultHeaderProvider.value
        }
        set(value) {
            internalDefaultHeaderProvider.setOrThrow(internalDefaultHeaderProvider.value, value)
        }

    /**
     * Shared Connectivity publisher
     */
    var connectivityPublisher: Publisher<ConnectivityState>
        get() {
            return internalConnectivityStatePublisher.value
        }
        set(value) {
            internalConnectivityStatePublisher.setOrThrow(
                internalConnectivityStatePublisher.value,
                value
            )
        }

    /**
     * Default BaseUrl used by HttpRequestPublisher
     */
    var baseUrl: String
        get() {
            return internalBaseUrl.value
        }
        set(value) {
            internalBaseUrl.setOrThrow(internalBaseUrl.value, value)
        }

    /**
     * Shared JSON parser used by DeserializableHttpRequestPublisher
     */
    var json: Json
        get() {
            return internalJson.value
        }
        set(value) {
            internalJson.setOrThrow(internalJson.value, value)
        }
}
