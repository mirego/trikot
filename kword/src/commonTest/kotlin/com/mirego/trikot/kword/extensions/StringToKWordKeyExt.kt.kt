package com.mirego.trikot.kword.extensions

import com.mirego.trikot.kword.KWordKey

data class MockKWordKey(override val translationKey: String) : KWordKey

val String.toKWordKey: KWordKey
    get() = MockKWordKey(this)
