package com.mirego.trikot.kword

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
    fun `given string with localized replacements then it replace placeholders`() {
        assertEquals("foo", defaultI18N["foo_key".kk])
        assertEquals("bar", defaultI18N["bar_key".kk])
        assertEquals("this is foo bar", defaultI18N["foo_bar_key".kk])
    }

    @Test
    fun `given string with arguments`() {
        assertEquals("Hi my name is Bob", defaultI18N.t("hi_my_name_is_key".kk, "name" to "Bob"))
    }

    @Test
    fun `given string with missing arguments then it keeps the placeholder`() {
        assertEquals("Hi my name is {{name}}", defaultI18N.t("hi_my_name_is_key".kk))
    }

    @Test
    fun `given string with localized replacements and arguments then it uses argument over localized replacement`() {
        assertEquals("this is foo rab", defaultI18N.t("foo_bar_key".kk, "bar_key" to "rab"))
    }

    private val String.kk: KWordKey
        get() {
            return object : KWordKey {
                override val translationKey: String
                    get() = this@kk
            }
        }
}
