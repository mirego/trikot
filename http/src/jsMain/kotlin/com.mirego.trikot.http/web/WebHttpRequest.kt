package com.mirego.trikot.http.web

import com.mirego.trikot.http.HttpRequest
import com.mirego.trikot.http.HttpResponse
import com.mirego.trikot.http.RequestBuilder
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import org.reactivestreams.Publisher
import org.w3c.xhr.ARRAYBUFFER
import org.w3c.xhr.XMLHttpRequest
import org.w3c.xhr.XMLHttpRequestResponseType

class WebHttpRequest(
    private val requestBuilder: RequestBuilder
) : HttpRequest {
    override fun execute(cancellableManager: CancellableManager): Publisher<HttpResponse> {
        val publisher = Publishers.behaviorSubject<HttpResponse>()

        try {
            val xhr = XMLHttpRequest()
            val method = requestBuilder.method.toString()
            val url = (requestBuilder.baseUrl ?: "") + (requestBuilder.path ?: "")
            val timeout = requestBuilder.timeout * 1000

            xhr.open(method, url, true)

            xhr.overrideMimeType("text/plain; charset=x-user-defined")
            xhr.responseType = XMLHttpRequestResponseType.ARRAYBUFFER

            xhr.timeout = timeout

            requestBuilder.headers.forEach { entry ->
                xhr.setRequestHeader(entry.key, entry.value)
            }

            xhr.addEventListener(
                "load",
                { _ ->
                    publisher.value = WebHttpResponse(xhr)
                }
            )

            xhr.addEventListener(
                "error",
                { _ ->
                    publisher.value = WebHttpResponse(xhr)
                }
            )

            cancellableManager.add { xhr.abort() }

            xhr.send(requestBuilder.body)
        } catch (error: Throwable) {
            publisher.error = error
        }

        return publisher
    }
}
