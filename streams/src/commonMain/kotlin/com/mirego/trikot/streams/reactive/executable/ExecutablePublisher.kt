package com.mirego.trikot.streams.reactive.executable

import com.mirego.trikot.streams.cancelable.Cancelable
import org.reactivestreams.Publisher

interface ExecutablePublisher<T> : Publisher<T>, Cancelable {
    fun execute()
}
