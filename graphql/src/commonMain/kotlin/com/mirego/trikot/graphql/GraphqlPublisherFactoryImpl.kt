package com.mirego.trikot.graphql

import com.mirego.trikot.http.HttpHeaderProvider
import com.mirego.trikot.streams.reactive.executable.ExecutablePublisher

class GraphqlPublisherFactoryImpl : GraphqlPublisherFactory {
    override fun <T> create(
        request: GraphqlQuery<T>,
        httpHeaderProvider: HttpHeaderProvider
    ): ExecutablePublisher<T> {
        return GraphqlQueryPublisher(request, httpHeaderProvider = httpHeaderProvider)
    }
}
