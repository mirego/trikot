package com.mirego.trikot.kword

import kotlin.js.JsName

interface I18N {
    operator fun get(kKey: KWordKey): String

    fun changeLocaleStrings(strings: Map<String, String>)

    fun changeLocaleStrings(source: KWordSource)

    @JsName("t")
    fun t(key: KWordKey): String

    fun t(key: KWordKey, count: Int, vararg arguments: String): String

    fun t(key: KWordKey, vararg arguments: Pair<String, String>): String
}
