package com.mirego.trikot.http.requestPublisher

import com.mirego.trikot.http.HttpConfiguration
import com.mirego.trikot.http.HttpHeaderProvider
import com.mirego.trikot.http.HttpRequestFactory
import com.mirego.trikot.http.HttpResponse
import com.mirego.trikot.http.RequestBuilder
import com.mirego.trikot.streams.Configuration
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.concurrent.dispatchQueue.DispatchQueue
import com.mirego.trikot.streams.reactive.executable.BaseExecutablePublisher
import com.mirego.trikot.streams.reactive.first
import com.mirego.trikot.streams.reactive.subscribe
import org.reactivestreams.Publisher

abstract class HttpRequestPublisher<T>(
    networkQueue: DispatchQueue = HttpConfiguration.networkDispatchQueue,
    private val operationQueue: DispatchQueue = Configuration.publisherExecutionDispatchQueue,
    private val httpRequestFactory: HttpRequestFactory = HttpConfiguration.httpRequestFactory,
    private val headerProvider: HttpHeaderProvider = HttpConfiguration.defaultHttpHeaderProvider
) : BaseExecutablePublisher<T>(networkQueue) {

    abstract val builder: RequestBuilder

    abstract fun processResponse(response: HttpResponse): T

    override fun internalRun(cancellableManager: CancellableManager) {
        val headerPublisher = headerProvider.headerForURLRequest(cancellableManager, builder)

        headerPublisher.first().subscribe(cancellableManager,
            onNext = { headers ->
                val requestBuilder = mergeBuilderWithHeaders(headers)

                executeRequest(cancellableManager, requestBuilder).subscribe(cancellableManager,
                    onNext = {
                        operationQueue.dispatch {
                            try {
                                dispatchSuccess(processResponse(it))
                            } catch (e: Exception) {
                                dispatchError(e)
                            }
                        }
                    },
                    onError = {
                        headerProvider.processHttpError(requestBuilder, it)
                        operationQueue.dispatch {
                            dispatchError(it)
                        }
                    })
            },
            onError = {
                dispatchError(it)
            })
    }

    private fun executeRequest(
        cancellableManager: CancellableManager,
        requestBuilder: RequestBuilder
    ): Publisher<HttpResponse> {
        return httpRequestFactory.request(requestBuilder).execute(cancellableManager)
    }

    private fun mergeBuilderWithHeaders(headers: Map<String, String>): RequestBuilder {
        return RequestBuilder().also {
            it.baseUrl = builder.baseUrl
            it.path = builder.path
            it.body = builder.body
            it.headers = builder.headers + headers
            it.method = builder.method
        }
    }
}
