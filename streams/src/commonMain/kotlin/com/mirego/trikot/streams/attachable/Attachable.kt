package com.mirego.trikot.streams.attachable

import com.mirego.trikot.streams.cancelable.Cancelable

interface Attachable {
    fun attach(): Cancelable
}
