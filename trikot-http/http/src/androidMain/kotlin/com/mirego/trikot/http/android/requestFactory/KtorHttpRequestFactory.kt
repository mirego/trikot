package com.mirego.trikot.http.android.requestFactory

import com.mirego.trikot.http.ApplicationJSON
import com.mirego.trikot.http.ApplicationOctetStream
import com.mirego.trikot.http.HttpRequest
import com.mirego.trikot.http.HttpRequestFactory
import com.mirego.trikot.http.HttpResponse
import com.mirego.trikot.http.RequestBuilder
import com.mirego.trikot.http.exception.HttpRequestTimeoutException
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.timeout
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.client.statement.readBytes
import io.ktor.content.ByteArrayContent
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.util.InternalAPI
import io.ktor.util.flattenEntries
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.reactivestreams.Publisher
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import io.ktor.client.plugins.HttpRequestTimeoutException as KtorRequestTimeoutException

private val DEFAULT_TIMEOUT_DURATION = 10.seconds

class KtorHttpRequestFactory(
    httpLogLevel: LogLevel = LogLevel.NONE,
    httpLogger: Logger = Logger.DEFAULT,
    private var httpClient: HttpClient = HttpClient(),
    requestTimeoutDuration: Duration = DEFAULT_TIMEOUT_DURATION,
    socketTimeoutDuration: Duration = requestTimeoutDuration,
    connectTimeoutDuration: Duration = requestTimeoutDuration
) : HttpRequestFactory {
    init {
        httpClient = httpClient.config {
            install(Logging) {
                logger = httpLogger
                level = httpLogLevel
            }
            install(HttpTimeout) {
                requestTimeoutMillis = requestTimeoutDuration.inWholeMilliseconds
                socketTimeoutMillis = socketTimeoutDuration.inWholeMilliseconds
                connectTimeoutMillis = connectTimeoutDuration.inWholeMilliseconds
            }
            expectSuccess = false
        }
    }

    override fun request(requestBuilder: RequestBuilder): HttpRequest {
        return KTorCoreHttpRequest(requestBuilder, httpClient)
    }

    class KTorCoreHttpRequest(
        private val requestBuilder: RequestBuilder,
        private val httpClient: HttpClient
    ) : HttpRequest, CoroutineScope {
        override val coroutineContext: CoroutineContext = Dispatchers.Unconfined

        @OptIn(InternalAPI::class)
        override fun execute(cancellableManager: CancellableManager): Publisher<HttpResponse> {
            val publisher = Publishers.behaviorSubject<HttpResponse>()

            launch {
                try {
                    val response = httpClient.request {
                        url(requestBuilder.buildUrl())

                        method = requestBuilder.method.ktorMethod

                        requestBuilder.headers
                            .filter { it.key != com.mirego.trikot.http.ContentType }
                            .forEach { entry ->
                                header(entry.key, entry.value)
                            }

                        requestBuilder.body?.let {
                            if (it is ByteArray) {
                                val contentType =
                                    requestBuilder.headers[com.mirego.trikot.http.ContentType]
                                        ?: ApplicationOctetStream
                                body = ByteArrayContent(it, ContentType.parse(contentType))
                            } else if (it is String) {
                                val contentType =
                                    requestBuilder.headers[com.mirego.trikot.http.ContentType]
                                        ?: ApplicationJSON
                                body = TextContent(it, ContentType.parse(contentType))
                            }
                        }

                        requestBuilder.timeout?.takeIf { it > 0 }?.let { timeout ->
                            timeout {
                                requestTimeoutMillis = timeout.seconds.inWholeMilliseconds
                            }
                        }
                    }

                    publisher.value =
                        KTorHttpResponse(response, response.call.response.readBytes())
                } catch (ex: Throwable) {
                    val response = (ex as? ResponseException)?.response
                    if (response != null) {
                        publisher.value =
                            KTorHttpResponse(response, response.call.response.readBytes())
                    } else {
                        publisher.error = when (ex) {
                            is KtorRequestTimeoutException -> HttpRequestTimeoutException(ex)
                            else -> ex
                        }
                    }
                }
            }

            return publisher
        }
    }

    class KTorHttpResponse(response: io.ktor.client.statement.HttpResponse, bytes: ByteArray?) :
        HttpResponse {
        override val statusCode: Int = response.status.value
        override val bodyByteArray: ByteArray? = bytes
        override val headers: Map<String, String> = response.headers.flattenEntries().toMap()
        override val source: HttpResponse.ResponseSource = HttpResponse.ResponseSource.UNKNOWN
    }
}

val com.mirego.trikot.http.HttpMethod.ktorMethod: HttpMethod
    get() = when (this) {
        com.mirego.trikot.http.HttpMethod.GET -> HttpMethod.Get
        com.mirego.trikot.http.HttpMethod.DELETE -> HttpMethod.Delete
        com.mirego.trikot.http.HttpMethod.HEAD -> HttpMethod.Head
        com.mirego.trikot.http.HttpMethod.PATCH -> HttpMethod.Patch
        com.mirego.trikot.http.HttpMethod.POST -> HttpMethod.Post
        com.mirego.trikot.http.HttpMethod.PUT -> HttpMethod.Put
    }
