package com.mirego.trikot.foundation.concurrent

expect object MrFreeze {
    fun <T> freeze(objectToFreeze: T): T
    actual fun ensureNeverFrozen(objectToProtect: Any)
}

expect fun <T> freeze(objectToFreeze: T): T
