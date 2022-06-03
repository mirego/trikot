package com.mirego.trikot.kword.flow

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.kword.KWordKey
import kotlinx.coroutines.flow.Flow

interface FlowI18N {
    val i18N: Flow<I18N>
    val currentI18N: I18N
    fun reset()
    fun getLanguageCode(): String
    fun changeLanguage(code: String)
    operator fun get(key: KWordKey): Flow<String>
    fun t(key: KWordKey): Flow<String>
    fun t(key: KWordKey, vararg arguments: Pair<String, String>): Flow<String>
    fun t(key: KWordKey, count: Int, vararg arguments: Pair<String, String>): Flow<String>
}
