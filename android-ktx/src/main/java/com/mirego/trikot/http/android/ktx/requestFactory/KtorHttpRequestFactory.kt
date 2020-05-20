package com.mirego.trikot.http.android.ktx.requestFactory

import com.mirego.trikot.http.ApplicationJSON
import com.mirego.trikot.http.ApplicationOctetStream
import com.mirego.trikot.http.HttpRequest
import com.mirego.trikot.http.HttpRequestFactory
import com.mirego.trikot.http.HttpResponse
import com.mirego.trikot.http.RequestBuilder
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import io.ktor.client.HttpClient
import io.ktor.client.features.ResponseException
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.client.response.readText
import io.ktor.content.ByteArrayContent
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.util.flattenEntries
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.reactivestreams.Publisher
import kotlin.coroutines.CoroutineContext

class KtorHttpRequestFactory(
    private val httpLogLevel: LogLevel = LogLevel.NONE,
    private val httpLogger: Logger = Logger.DEFAULT
) : HttpRequestFactory {
    override fun request(requestBuilder: RequestBuilder): HttpRequest {
        return KTorCoreHttpRequest(requestBuilder, httpLogLevel, httpLogger)
    }

    class KTorCoreHttpRequest(
        private val requestBuilder: RequestBuilder,
        private val httpLogLevel: LogLevel,
        private val httpLogger: Logger
    ) : HttpRequest, CoroutineScope {
        override val coroutineContext: CoroutineContext = Dispatchers.Unconfined

        override fun execute(cancellableManager: CancellableManager): Publisher<HttpResponse> {
            val publisher = Publishers.behaviorSubject<HttpResponse>()

            launch {
                val client = HttpClient {
                    install(Logging) {
                        logger = httpLogger
                        level = httpLogLevel
                    }
                }
                try {
                    val response = client.request<io.ktor.client.response.HttpResponse> {
                        url((requestBuilder.baseUrl ?: "") + (requestBuilder.path ?: ""))
                        requestBuilder.headers.filter { it.key != com.mirego.trikot.http.ContentType }
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
                        method = requestBuilder.method.ktorMethod
                    }

                    publisher.value = KTorHttpResponse(response, response.call.response.readText())
                } catch (ex: Exception) {
                    val response = (ex as? ResponseException)?.response
                    if (response != null) {
                        publisher.value = KTorHttpResponse(response, response.call.response.readText())
                    } else {
                        publisher.error = ex
                    }
                }
            }

            return publisher
        }
    }

    class KTorHttpResponse(response: io.ktor.client.response.HttpResponse, body: String?) : HttpResponse {
        override val statusCode: Int = response.status.value
        override val bodyString: String? = body
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
