package com.mirego.trikot.streams.concurrent

expect object MrFreeze {
    fun <T> freeze(objectToFreeze: T): T
}

expect fun <T> freeze(objectToFreeze: T): T
