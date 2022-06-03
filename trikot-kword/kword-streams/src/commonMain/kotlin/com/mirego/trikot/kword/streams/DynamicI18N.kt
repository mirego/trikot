package com.mirego.trikot.kword.streams

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.kword.KWordKey
import com.mirego.trikot.streams.reactive.BehaviorSubject
import org.reactivestreams.Publisher

interface DynamicI18N {
    val language: BehaviorSubject<String>
    val i18N: Publisher<I18N>
    val currentI18N: I18N
    fun reset()
    operator fun get(key: KWordKey): Publisher<String>
    fun t(key: KWordKey): Publisher<String>
    fun t(key: KWordKey, vararg arguments: Pair<String, String>): Publisher<String>
    fun t(key: KWordKey, count: Int, vararg arguments: Pair<String, String>): Publisher<String>
}
