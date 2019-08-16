package com.mirego.trikot.kword

import kotlin.js.JsName
import com.mirego.trikot.foundation.concurrent.AtomicReference

interface KWordKey {
    val translationKey: String
}

interface KWordSource {
    @JsName("get")
    fun get(key: String): String
}

class MapKeywordSource(private val source: Map<String, String>) : KWordSource {
    override operator fun get(key: String): String {
        return source[key] ?: key
    }
}

object KWord : I18N {
    private val sourceRef: AtomicReference<KWordSource> =
        AtomicReference(MapKeywordSource(HashMap()))
    private val source: KWordSource
        get() = sourceRef.value
    private val translationArgumentsParser: TranslationArgumentsParser = TranslationArgumentsParser()

    override fun changeLocaleStrings(strings: Map<String, String>) {
        changeLocaleStrings(MapKeywordSource(strings))
    }

    override fun changeLocaleStrings(source: KWordSource) {
        sourceRef.compareAndSet(sourceRef.value, source)
    }

    override operator fun get(kKey: KWordKey): String {
        return source.get(kKey.translationKey)
    }

    override fun t(key: KWordKey): String {
        return this[key]
    }

    override fun t(key: KWordKey, count: Int, vararg arguments: String): String {
        // TODO: implement zero, one, other
        return t(key)
    }

    override fun t(key: KWordKey, vararg arguments: Pair<String, String>): String {
        return translationArgumentsParser.replaceInTranslation(t(key), arguments)
    }
}
