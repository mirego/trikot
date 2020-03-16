package com.mirego.trikot.foundation.concurrent

actual object MrFreeze {
    actual fun <T> freeze(objectToFreeze: T): T {
        return objectToFreeze
    }
}

actual fun <T> freeze(objectToFreeze: T): T {
    return MrFreeze.freeze(objectToFreeze)
}
