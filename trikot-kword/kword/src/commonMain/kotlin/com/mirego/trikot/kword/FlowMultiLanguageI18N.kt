package com.mirego.trikot.kword

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FlowMultiLanguageI18N(private val initialLanguage: String, private val languages: Map<String, I18N>) : FlowI18N {
    override val language = MutableStateFlow(initialLanguage)
    override val i18N = language.map { languages.getValue(it) }
    override val currentI18N get() = language.value.let { languages.getValue(it) }

    override fun reset() {
        language.value = initialLanguage
    }

    override fun changeLanguage(code: String) {
        language.value = code
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
