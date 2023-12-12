package com.mirego.trikot.kword

class DebugI18N : I18N {
    override operator fun get(key: KWordKey) = getTranslationKey(key)

    override fun changeLocaleStrings(strings: Map<String, String>) {
        // NO-OP
    }

    override fun changeLocaleStrings(source: KWordSource) {
        // NO-OP
    }

    override fun t(key: KWordKey) = getTranslationKey(key)

    override fun t(key: KWordKey, count: Int, vararg arguments: Pair<String, String>) = getTranslationKey(key)

    override fun t(key: KWordKey, vararg arguments: Pair<String, String>) = getTranslationKey(key)

    private fun getTranslationKey(key: KWordKey) = key.translationKey
}
