package com.mirego.trikot.foundation.concurrent

class AtomicListReference<T> : AbstractAtomicListReference<T>() {
    fun addAll(value: List<T>): List<T> = mutateWhenReady { it + value }
    fun removeAll(value: List<T>) = mutateWhenReady { it - value }
    fun add(value: T) = addAll(listOf(value))
    fun remove(value: T) = removeAll(listOf(value))
}
