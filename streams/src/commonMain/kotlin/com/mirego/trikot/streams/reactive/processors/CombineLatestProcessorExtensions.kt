package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.filter
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

@Suppress("UNCHECKED_CAST")
fun <T, R> Publisher<T>.safeCombine(publisher: Publisher<R>): Publisher<Pair<T, R>> {
    return (this as Publisher<Any>).combine(listOf(publisher) as List<Publisher<Any>>)
        .filter { list -> list[0] as? T != null && list[1] as? T != null }
        .map { list ->
            list[0] as T to list[1] as R
        }
}

@Suppress("UNCHECKED_CAST")
fun <T, R1, R2> Publisher<T>.safeCombine(publisher1: Publisher<R1>, publisher2: Publisher<R2>): Publisher<Triple<T, R1, R2>> {
    return (this as Publisher<Any>).combine(listOf(publisher1, publisher2) as List<Publisher<Any>>)
        .filter { list -> list[0] as? T != null && list[1] as? T != null && list[2] as? T != null }
        .map { list ->
            Triple(list[0] as T, list[1] as R1, list[2] as R2)
        }
}

fun <T> Publisher<T>.combine(publishers: List<Publisher<T>>): Publisher<List<T?>> {
    return CombineLatestProcessor(this, publishers)
}
