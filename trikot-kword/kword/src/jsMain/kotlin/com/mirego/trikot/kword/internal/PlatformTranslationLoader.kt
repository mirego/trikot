package com.mirego.trikot.kword.internal

import kotlinext.js.require

internal actual object PlatformTranslationLoader {
    actual fun loadTranslations(path: String): Map<String, String>? =
        try {
            mapOf(require(path))
        } catch (error: dynamic) {
            println("Unable to parse JSON: $error")
            null
        }

    private fun mapOf(jsObject: dynamic): Map<String, String> =
        entriesOf(jsObject).toMap()

    private fun entriesOf(jsObject: dynamic): List<Pair<String, String>> =
        (js("Object.entries") as (dynamic) -> Array<Array<String>>)
            .invoke(jsObject)
            .map { entry -> entry[0] to entry[1] }
}
