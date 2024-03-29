package com.mirego.trikot.kword

import com.mirego.trikot.kword.extensions.toKWordKey
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class Debug18NTests {

    companion object {
        val STRINGS = mapOf(
            "foo_key" to "foo",
            "bar_key" to "bar",
            "foo_bar_key" to "this is {{foo_key}} {{bar_key}}",
            "hi_my_name_is_key" to "Hi my name is {{name}}"
        )
    }

    private val debugI18N = DebugI18N()

    @BeforeTest
    fun setup() {
        debugI18N.changeLocaleStrings(STRINGS)
    }

    @Test
    fun givenStringWithLocalizedReplacements() {
        assertEquals("foo_key", debugI18N["foo_key".toKWordKey])
        assertEquals("bar_key", debugI18N["bar_key".toKWordKey])
        assertEquals("foo_bar_key", debugI18N["foo_bar_key".toKWordKey])
    }

    @Test
    fun givenStringWithArguments() {
        assertEquals("hi_my_name_is_key", debugI18N.t("hi_my_name_is_key".toKWordKey, "name" to "Bob"))
    }

    @Test
    fun givenStringWithMissingArguments() {
        assertEquals("hi_my_name_is_key", debugI18N.t("hi_my_name_is_key".toKWordKey))
    }

    @Test
    fun givenStringWithLocalizedReplacementsAndArguments() {
        assertEquals("foo_bar_key", debugI18N.t("foo_bar_key".toKWordKey, "bar_key" to "rab"))
    }
}
