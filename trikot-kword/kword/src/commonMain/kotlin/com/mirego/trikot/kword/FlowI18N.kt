package com.mirego.trikot.kword

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface FlowI18N {
    val language: MutableStateFlow<String>
    val i18N: Flow<I18N>
    val currentI18N: I18N
    fun reset()
    fun changeLanguage(code: String)
    operator fun get(key: KWordKey): Flow<String>
    fun t(key: KWordKey): Flow<String>
    fun t(key: KWordKey, vararg arguments: Pair<String, String>): Flow<String>
    fun t(key: KWordKey, count: Int, vararg arguments: Pair<String, String>): Flow<String>
}
