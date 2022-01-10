package com.mirego.trikot.streams.reactive.executable

import com.mirego.trikot.streams.cancellable.Cancellable
import org.reactivestreams.Publisher

interface ExecutablePublisher<T> : Publisher<T>, Cancellable {
    fun execute()
}
