package com.mirego.trikot.kword

interface KWordKey {
    val translationKey: String
}

interface KWordSource {
    fun get(key: String): String

    fun getOptional(key: String): String?

    val strings: Map<String, String>
}

class MapKeywordSource(override val strings: Map<String, String>) : KWordSource {
    override operator fun get(key: String): String = strings[key] ?: key

    override fun getOptional(key: String): String? = strings[key]
}

object KWord : DefaultI18N()
