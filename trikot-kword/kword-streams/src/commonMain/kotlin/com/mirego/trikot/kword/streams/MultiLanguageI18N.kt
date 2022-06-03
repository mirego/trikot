package com.mirego.trikot.kword.streams

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.kword.KWordKey
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.map
import com.mirego.trikot.streams.reactive.shared

class MultiLanguageI18N(private val initialLanguage: String, private val languages: Map<String, I18N>) : DynamicI18N {
    override val language = Publishers.behaviorSubject(initialLanguage)
    override val i18N = language.map { languages.getValue(it) }.shared()
    override val currentI18N get() = language.value?.let { languages.getValue(it) } ?: throw IllegalStateException()

    override fun reset() {
        language.value = initialLanguage
    }

    override operator fun get(key: KWordKey) =
        i18N.map { it[key] }

    override fun t(key: KWordKey) =
        i18N.map { it.t(key) }

    override fun t(key: KWordKey, vararg arguments: Pair<String, String>) =
        i18N.map { it.t(key, *arguments) }

    override fun t(key: KWordKey, count: Int, vararg arguments: Pair<String, String>) =
        i18N.map { it.t(key, count, *arguments) }
}
