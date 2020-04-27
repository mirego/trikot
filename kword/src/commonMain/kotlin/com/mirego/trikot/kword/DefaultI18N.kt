package com.mirego.trikot.kword

import com.mirego.trikot.foundation.concurrent.AtomicReference

open class DefaultI18N : I18N {
    private val sourceRef: AtomicReference<KWordSource> =
        AtomicReference(MapKeywordSource(HashMap()))
    private val source: KWordSource
        get() = sourceRef.value
    private val translationArgumentsParser: TranslationArgumentsParser =
        TranslationArgumentsParser()

    override fun changeLocaleStrings(strings: Map<String, String>) {
        changeLocaleStrings(MapKeywordSource(strings))
    }

    override fun changeLocaleStrings(source: KWordSource) {
        sourceRef.compareAndSet(sourceRef.value, source)
    }

    override operator fun get(key: KWordKey): String {
        return getWithReplacements(key)
    }

    override fun t(key: KWordKey): String {
        return this[key]
    }

    override fun t(key: KWordKey, count: Int, vararg arguments: String): String {
        // TODO: implement zero, one, other
        return t(key)
    }

    override fun t(key: KWordKey, vararg arguments: Pair<String, String>): String {
        return getWithReplacements(key, mapOf(*arguments))
    }

    private fun getWithReplacements(
        key: KWordKey,
        arguments: Map<String, String> = emptyMap()
    ): String {
        return translationArgumentsParser.replaceInTranslation(
            source.get(key.translationKey),
            sourceRef.value.strings + arguments
        )
    }
}
