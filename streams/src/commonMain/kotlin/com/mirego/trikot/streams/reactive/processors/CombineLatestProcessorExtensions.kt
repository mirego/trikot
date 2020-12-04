package com.mirego.trikot.streams.reactive.processors

import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.filter
import com.mirego.trikot.streams.reactive.map
import org.reactivestreams.Publisher

data class NTuple4<out A, out B, out C, out D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
)

data class NTuple5<out A, out B, out C, out D, out E>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E
)

data class NTuple6<out A, out B, out C, out D, out E, out F>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F
)

data class NTuple7<out A, out B, out C, out D, out E, out F, out G>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G
)

data class NTuple8<out A, out B, out C, out D, out E, out F, out G, out H>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H
)

data class NTuple9<out A, out B, out C, out D, out E, out F, out G, out H, out I>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H,
    val ninth: I
)

data class NTuple10<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H,
    val ninth: I,
    val tenth: J
)

fun <T> combine(publishers: List<Publisher<T>>): Publisher<List<T?>> {
    return if (publishers.count() == 0) {
        Publishers.behaviorSubject(emptyList())
    } else {
        val publisher = publishers.first()
        val otherPublishers = if (publishers.count() > 1) publishers.subList(
            1,
            publishers.count()
        ) else emptyList()

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

@Suppress("UNCHECKED_CAST")
fun <T, R1, R2, R3> Publisher<T>.safeCombine(
    publisher1: Publisher<R1>,
    publisher2: Publisher<R2>,
    publisher3: Publisher<R3>
): Publisher<NTuple4<T, R1, R2, R3>> {
    return (this as Publisher<Any>).combine(listOf(publisher1, publisher2, publisher3) as List<Publisher<Any>>)
        .filter { list -> list[0] as? T != null && list[1] as? R1 != null && list[2] as? R2 != null && list[3] as? R3 != null }
        .map { list ->
            NTuple4(list[0] as T, list[1] as R1, list[2] as R2, list[3] as R3)
        }
}

@Suppress("UNCHECKED_CAST")
fun <T, R1, R2, R3, R4> Publisher<T>.safeCombine(
    publisher1: Publisher<R1>,
    publisher2: Publisher<R2>,
    publisher3: Publisher<R3>,
    publisher4: Publisher<R4>
): Publisher<NTuple5<T, R1, R2, R3, R4>> {
    return (this as Publisher<Any>).combine(listOf(publisher1, publisher2, publisher3, publisher4) as List<Publisher<Any>>)
        .filter { list -> list[0] as? T != null && list[1] as? R1 != null && list[2] as? R2 != null && list[3] as? R3 != null && list[4] as? R4 != null }
        .map { list ->
            NTuple5(list[0] as T, list[1] as R1, list[2] as R2, list[3] as R3, list[4] as R4)
        }
}

@Suppress("UNCHECKED_CAST")
fun <T, R1, R2, R3, R4, R5> Publisher<T>.safeCombine(
    publisher1: Publisher<R1>,
    publisher2: Publisher<R2>,
    publisher3: Publisher<R3>,
    publisher4: Publisher<R4>,
    publisher5: Publisher<R5>
): Publisher<NTuple6<T, R1, R2, R3, R4, R5>> {
    return (this as Publisher<Any>).combine(listOf(publisher1, publisher2, publisher3, publisher4, publisher5) as List<Publisher<Any>>)
        .filter { list -> list[0] as? T != null && list[1] as? R1 != null && list[2] as? R2 != null && list[3] as? R3 != null && list[4] as? R4 != null && list[5] as? R5 != null }
        .map { list ->
            NTuple6(list[0] as T, list[1] as R1, list[2] as R2, list[3] as R3, list[4] as R4, list[5] as R5)
        }
}

@Suppress("UNCHECKED_CAST")
fun <T, R1, R2, R3, R4, R5, R6> Publisher<T>.safeCombine(
    publisher1: Publisher<R1>,
    publisher2: Publisher<R2>,
    publisher3: Publisher<R3>,
    publisher4: Publisher<R4>,
    publisher5: Publisher<R5>,
    publisher6: Publisher<R6>
): Publisher<NTuple7<T, R1, R2, R3, R4, R5, R6>> {
    return (this as Publisher<Any>).combine(listOf(publisher1, publisher2, publisher3, publisher4, publisher5, publisher6) as List<Publisher<Any>>)
        .filter { list -> list[0] as? T != null && list[1] as? R1 != null && list[2] as? R2 != null && list[3] as? R3 != null && list[4] as? R4 != null && list[5] as? R5 != null && list[6] as? R6 != null }
        .map { list ->
            NTuple7(list[0] as T, list[1] as R1, list[2] as R2, list[3] as R3, list[4] as R4, list[5] as R5, list[6] as R6)
        }
}

@Suppress("UNCHECKED_CAST")
fun <T, R1, R2, R3, R4, R5, R6, R7> Publisher<T>.safeCombine(
    publisher1: Publisher<R1>,
    publisher2: Publisher<R2>,
    publisher3: Publisher<R3>,
    publisher4: Publisher<R4>,
    publisher5: Publisher<R5>,
    publisher6: Publisher<R6>,
    publisher7: Publisher<R7>
): Publisher<NTuple8<T, R1, R2, R3, R4, R5, R6, R7>> {
    return (this as Publisher<Any>).combine(listOf(publisher1, publisher2, publisher3, publisher4, publisher5, publisher6, publisher7) as List<Publisher<Any>>)
        .filter { list -> list[0] as? T != null && list[1] as? R1 != null && list[2] as? R2 != null && list[3] as? R3 != null && list[4] as? R4 != null && list[5] as? R5 != null && list[6] as? R6 != null && list[7] as? R7 != null }
        .map { list ->
            NTuple8(list[0] as T, list[1] as R1, list[2] as R2, list[3] as R3, list[4] as R4, list[5] as R5, list[6] as R6, list[7] as R7)
        }
}

@Suppress("UNCHECKED_CAST")
fun <T, R1, R2, R3, R4, R5, R6, R7, R8> Publisher<T>.safeCombine(
    publisher1: Publisher<R1>,
    publisher2: Publisher<R2>,
    publisher3: Publisher<R3>,
    publisher4: Publisher<R4>,
    publisher5: Publisher<R5>,
    publisher6: Publisher<R6>,
    publisher7: Publisher<R7>,
    publisher8: Publisher<R8>
): Publisher<NTuple9<T, R1, R2, R3, R4, R5, R6, R7, R8>> {
    return (this as Publisher<Any>).combine(listOf(publisher1, publisher2, publisher3, publisher4, publisher5, publisher6, publisher7, publisher8) as List<Publisher<Any>>)
        .filter { list -> list[0] as? T != null && list[1] as? R1 != null && list[2] as? R2 != null && list[3] as? R3 != null && list[4] as? R4 != null && list[5] as? R5 != null && list[6] as? R6 != null && list[7] as? R7 != null && list[8] as? R8 != null }
        .map { list ->
            NTuple9(list[0] as T, list[1] as R1, list[2] as R2, list[3] as R3, list[4] as R4, list[5] as R5, list[6] as R6, list[7] as R7, list[8] as R8)
        }
}

@Suppress("UNCHECKED_CAST")
fun <T, R1, R2, R3, R4, R5, R6, R7, R8, R9> Publisher<T>.safeCombine(
    publisher1: Publisher<R1>,
    publisher2: Publisher<R2>,
    publisher3: Publisher<R3>,
    publisher4: Publisher<R4>,
    publisher5: Publisher<R5>,
    publisher6: Publisher<R6>,
    publisher7: Publisher<R7>,
    publisher8: Publisher<R8>,
    publisher9: Publisher<R9>
): Publisher<NTuple10<T, R1, R2, R3, R4, R5, R6, R7, R8, R9>> {
    return (this as Publisher<Any>).combine(listOf(publisher1, publisher2, publisher3, publisher4, publisher5, publisher6, publisher7, publisher8, publisher9) as List<Publisher<Any>>)
        .filter { list -> list[0] as? T != null && list[1] as? R1 != null && list[2] as? R2 != null && list[3] as? R3 != null && list[4] as? R4 != null && list[5] as? R5 != null && list[6] as? R6 != null && list[7] as? R7 != null && list[8] as? R8 != null && list[9] as? R9 != null }
        .map { list ->
            NTuple10(list[0] as T, list[1] as R1, list[2] as R2, list[3] as R3, list[4] as R4, list[5] as R5, list[6] as R6, list[7] as R7, list[8] as R8, list[9] as R9)
        }
}

fun <T> Publisher<T>.combine(publishers: List<Publisher<T>>): Publisher<List<T?>> {
    return CombineLatestProcessor(this, publishers)
}
