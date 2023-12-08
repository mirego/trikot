package com.mirego.trikot.kword

class DebugI18N : I18N {
    override operator fun get(key: KWordKey): String {
        return getTranslationKey(key)
    }

    override fun changeLocaleStrings(strings: Map<String, String>) {
        // NO-OP
    }

    override fun changeLocaleStrings(source: KWordSource) {
        // NO-OP
    }

    override fun t(key: KWordKey): String {
        return getTranslationKey(key)
    }

    override fun t(key: KWordKey, count: Int, vararg arguments: Pair<String, String>): String {
        return getTranslationKey(key)
    }

    override fun t(key: KWordKey, vararg arguments: Pair<String, String>): String {
        return getTranslationKey(key)
    }

    private fun getTranslationKey(key: KWordKey): String {
        return key.translationKey
    }
}