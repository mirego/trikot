package com.mirego.trikot.foundation.concurrent

abstract class AbstractAtomicListReference<T> {
    private val internalReference = AtomicReference<List<T>>(ArrayList())

    val value: List<T> get() = internalReference.value

    protected fun mutateWhenReady(block: (value: List<T>) -> List<T>): List<T> {
        val oldList = value
        val newList = block(oldList)
        return if (internalReference.compareAndSet(oldList, newList)) {
            newList
        } else {
            mutateWhenReady(block)
        }
    }

    fun set(newValue: List<T>) {
        internalReference.set(newValue)
    }
}
