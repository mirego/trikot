package com.mirego.trikot.foundation.concurrent

expect object MrFreeze {
    fun <T> freeze(objectToFreeze: T): T
}

expect fun <T> freeze(objectToFreeze: T): T
