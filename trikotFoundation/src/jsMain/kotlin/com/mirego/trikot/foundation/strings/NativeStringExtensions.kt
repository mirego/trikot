package com.mirego.trikot.foundation.strings

actual fun String.normalize(): String {
    @Suppress("UNUSED_VARIABLE", "js does not recognize keyword 'this'")
    val string = this
    return js("string.normalize(\"NFD\").replace(/[\\u0300-\\u036f]/g, \"\")") as String
}
