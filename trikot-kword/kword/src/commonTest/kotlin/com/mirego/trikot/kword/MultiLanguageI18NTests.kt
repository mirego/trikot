package com.mirego.trikot.kword

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MultiLanguageI18NTests {
    private companion object {
        private const val FR = "fr"
        private const val EN = "en"
        private const val FR_VALUE = "fr value"
        private const val EN_VALUE = "en value"
        private const val FR_ARG_VALUE = "fr arg = {{arg}}"
        private const val EN_ARG_VALUE = "en arg = {{arg}}"
    }

    private val frenchI18N = DefaultI18N()
    private val englishI18N = DefaultI18N()
    private val languages = mapOf(
        FR to frenchI18N,
        EN to englishI18N
    )

    @BeforeTest
    fun setup() {
        frenchI18N.changeLocaleStrings(mapOf(
            SomeKey.translationKey to FR_VALUE,
            ArgKey.translationKey to FR_ARG_VALUE
        ))
        englishI18N.changeLocaleStrings(mapOf(
            SomeKey.translationKey to EN_VALUE,
            ArgKey.translationKey to EN_ARG_VALUE
        ))
    }

    @Test
    fun givenFrenchInitialLanguageThenItStartsInFrench() {
        val multiLanguageI18N = MultiLanguageI18N(FR, languages)
        assertEquals(FR, multiLanguageI18N.language.value)
        assertEquals(frenchI18N, multiLanguageI18N.currentI18N)
        multiLanguageI18N.i18N.assertEquals(frenchI18N)
    }

    @Test
    fun givenFrenchInitialLanguageThenItForwardsCallToFrenchI18N() {
        val multiLanguageI18N = MultiLanguageI18N(FR, languages)
        multiLanguageI18N[SomeKey].assertEquals(FR_VALUE)
        multiLanguageI18N.t(SomeKey).assertEquals(FR_VALUE)
        multiLanguageI18N.t(ArgKey, "arg" to "value").assertEquals("fr arg = value")
        multiLanguageI18N.t(ArgKey, 2, "arg" to "value").assertEquals("fr arg = value")
    }

    @Test
    fun givenLanguageSwitchedToEnglishThenItForwardsCallToEnglishI18N() {
        val multiLanguageI18N = MultiLanguageI18N(FR, languages)
        multiLanguageI18N.language.value = EN
        multiLanguageI18N[SomeKey].assertEquals(EN_VALUE)
        multiLanguageI18N.t(SomeKey).assertEquals(EN_VALUE)
        multiLanguageI18N.t(ArgKey, "arg" to "value").assertEquals("en arg = value")
        multiLanguageI18N.t(ArgKey, 2, "arg" to "value").assertEquals("en arg = value")
    }

    @Test
    fun givenFrenchInitialLanguageWhenSwitchingToEnglishAndResettingThenItGoesBackToFrench() {
        val multiLanguageI18N = MultiLanguageI18N(FR, languages)
        multiLanguageI18N.language.value = EN
        multiLanguageI18N.reset()
        assertEquals(FR, multiLanguageI18N.language.value)
    }

    private object SomeKey : KWordKey {
        override val translationKey = "SomeKey"
    }

    private object ArgKey : KWordKey {
        override val translationKey = "ArgKey"
    }
}
