package com.mirego.trikot.kword

import kotlin.js.JsName

interface I18N {
    operator fun get(key: KWordKey): String

    fun changeLocaleStrings(strings: Map<String, String>)

    @JsName("changeLocaleStringsFromSource")
    fun changeLocaleStrings(source: KWordSource)

    fun t(key: KWordKey): String

    @JsName("tc")
    fun t(key: KWordKey, count: Int, vararg arguments: Pair<String, String>): String

    @JsName("ta")
    fun t(key: KWordKey, vararg arguments: Pair<String, String>): String
}
