package com.mirego.trikot.foundation.concurrent

class AtomicStackReference<T> {
    private val internalReference = AtomicReference<List<T>>(ArrayList())

    val value: List<T> get() = internalReference.value

    fun push(item: T): List<T> = mutateWhenReady { it + item }
    fun pop(): T? = mutateWhenReady { it.dropLast(1) }.lastOrNull()
    fun peek(): T? = value.lastOrNull()

    private fun mutateWhenReady(block: (value: List<T>) -> List<T>): List<T> {
        val oldList = value
        val newList = block(oldList)
        return if (internalReference.compareAndSet(oldList, newList)) {
            newList
        } else {
            mutateWhenReady(block)
        }
    }
}