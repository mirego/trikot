package com.mirego.trikot.streams.attachable

import com.mirego.trikot.streams.cancellable.Cancellable

interface Attachable {
    fun attach(): Cancellable
}
