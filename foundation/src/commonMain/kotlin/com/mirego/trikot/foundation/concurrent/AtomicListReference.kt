package com.mirego.trikot.foundation.concurrent

class AtomicListReference<T> {
    private val internalReference = AtomicReference<List<T>>(ArrayList())

    val value: List<T> get() = internalReference.value

    fun addAll(value: List<T>): List<T> {
        return mutateWhenReady { it + value }
    }

    fun removeAll(value: List<T>): List<T> {
        return mutateWhenReady { it - value }
    }

    fun add(value: T): List<T> {
        return addAll(listOf(value))
    }

    fun remove(value: T): List<T> {
        return removeAll(listOf(value))
    }

    private fun mutateWhenReady(block: (value: List<T>) -> List<T>): List<T> {
        var oldList: List<T>
        var newList: List<T>
        do {
            oldList = value
            newList = block(oldList)
            if (oldList == newList) {
                return oldList
            }
            freeze(newList)
        } while (!internalReference.compareAndSet(oldList, newList))

        return newList
    }
}
