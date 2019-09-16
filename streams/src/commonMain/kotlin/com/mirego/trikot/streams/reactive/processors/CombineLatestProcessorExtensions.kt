package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.map
import org.reactivestreams.Publisher

fun <T> combine(publishers: List<Publisher<T>>): Publisher<List<T?>> {
    return if (publishers.count() == 0) {
        Publishers.behaviorSubject(emptyList())
    } else {
        val publisher = publishers.first()
        val otherPublishers = if (publishers.count() > 1) publishers.subList(
            1, publishers.count()) else emptyList()

        publisher.combine(otherPublishers)
    }
}

@Suppress("UNCHECKED_CAST")
fun <T, R> Publisher<T>.combine(publisher: Publisher<R>): Publisher<Pair<T?, R?>> {
    return (this as Publisher<Any>).combine(listOf(publisher) as List<Publisher<Any>>)
        .map { list ->
            list[0] as? T to list[1] as? R
        }
}

fun <T> Publisher<T>.combine(publishers: List<Publisher<T>>): Publisher<List<T?>> {
    return CombineLatestProcessor(this, publishers)
}
