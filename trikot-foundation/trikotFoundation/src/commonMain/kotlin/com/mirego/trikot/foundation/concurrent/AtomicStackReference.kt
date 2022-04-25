package com.mirego.trikot.foundation.concurrent

class AtomicStackReference<T> : AbstractAtomicListReference<T>() {
    fun push(item: T): List<T> = mutateWhenReady { it + item }
    fun pop(): T? = mutateWhenReady { it.dropLast(1) }.lastOrNull()
    fun peek(): T? = value.lastOrNull()
}
