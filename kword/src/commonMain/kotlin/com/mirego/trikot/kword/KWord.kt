package com.mirego.trikot.kword

import kotlin.js.JsName

interface KWordKey {
    val translationKey: String
}

interface KWordSource {
    @JsName("get")
    fun get(key: String): String

    @JsName("getOptional")
    fun getOptional(key: String): String?

    val strings: Map<String, String>
}

class MapKeywordSource(override val strings: Map<String, String>) : KWordSource {
    override operator fun get(key: String): String = strings[key] ?: key

    override fun getOptional(key: String): String? = strings[key]
}

object KWord : DefaultI18N()
