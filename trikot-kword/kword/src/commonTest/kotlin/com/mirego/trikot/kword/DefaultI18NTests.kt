package com.mirego.trikot.kword

import com.mirego.trikot.kword.extensions.toKWordKey
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultI18NTests {

    companion object {
        val STRINGS = mapOf(
            "foo_key" to "foo",
            "bar_key" to "bar",
            "foo_bar_key" to "this is {{foo_key}} {{bar_key}}",
            "hi_my_name_is_key" to "Hi my name is {{name}}"
        )
    }

    private val defaultI18N = DefaultI18N()

    @BeforeTest
    fun setup() {
        defaultI18N.changeLocaleStrings(STRINGS)
    }

    @Test
    fun givenStringWithLocalizedReplacementsThenItReplacePlaceholders() {
        assertEquals("foo", defaultI18N["foo_key".toKWordKey])
        assertEquals("bar", defaultI18N["bar_key".toKWordKey])
        assertEquals("this is foo bar", defaultI18N["foo_bar_key".toKWordKey])
    }

    @Test
    fun givenStringWithArguments() {
        assertEquals("Hi my name is Bob", defaultI18N.t("hi_my_name_is_key".toKWordKey, "name" to "Bob"))
    }

    @Test
    fun givenStringWithMissingArgumentsThenItKeepsThePlaceholder() {
        assertEquals("Hi my name is {{name}}", defaultI18N.t("hi_my_name_is_key".toKWordKey))
    }

    @Test
    fun givenStringWithLocalizedReplacementsAndArgumentsThenItUsesArgumentOverLocalizedReplacement() {
        assertEquals("this is foo rab", defaultI18N.t("foo_bar_key".toKWordKey, "bar_key" to "rab"))
    }
}
