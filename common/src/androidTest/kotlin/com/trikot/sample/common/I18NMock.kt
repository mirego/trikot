package com.trikot.sample.common

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.kword.KWordKey
import com.mirego.trikot.kword.KWordSource

class I18NMock : I18N {

    override fun changeLocaleStrings(source: KWordSource) {
    }

    override fun changeLocaleStrings(strings: Map<String, String>) {
    }

    override fun get(key: KWordKey): String {
        return key.translationKey
    }

    override fun t(key: KWordKey): String {
        return key.translationKey
    }

    override fun t(key: KWordKey, vararg arguments: Pair<String, String>): String {
        return key.translationKey
    }

    override fun t(key: KWordKey, count: Int, vararg arguments: Pair<String, String>): String {
        return key.translationKey
    }
}
