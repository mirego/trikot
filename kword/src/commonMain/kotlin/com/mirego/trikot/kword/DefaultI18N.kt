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

    override fun t(key: KWordKey, count: Int, vararg arguments: Pair<String, String>): String {
        val keyWithCount = "${key.translationKey}_$count"
        val targetString = source.getOptional(keyWithCount)

        val argumentsWithCount = arguments.toMutableSet()
        argumentsWithCount.add(COUNT_ARGUMENT to count.toString())
        val keyToUse = if (targetString != null) keyWithCount else key.translationKey
        return translationArgumentsParser.replaceInTranslation(source.get(keyToUse), mapOf(*argumentsWithCount.toTypedArray()))
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
            arguments,
            source.strings
        )
    }

    companion object {
        private const val COUNT_ARGUMENT = "count"
    }
}
