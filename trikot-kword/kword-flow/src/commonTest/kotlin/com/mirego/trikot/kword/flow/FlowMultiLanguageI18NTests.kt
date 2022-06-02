package com.mirego.trikot.kword.flow

import com.mirego.trikot.kword.DefaultI18N
import com.mirego.trikot.kword.KWordKey
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class FlowMultiLanguageI18NTests {
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
        frenchI18N.changeLocaleStrings(
            mapOf(
                SomeKey.translationKey to FR_VALUE,
                ArgKey.translationKey to FR_ARG_VALUE
            )
        )
        englishI18N.changeLocaleStrings(
            mapOf(
                SomeKey.translationKey to EN_VALUE,
                ArgKey.translationKey to EN_ARG_VALUE
            )
        )
    }

    @Test
    fun givenFrenchInitialLanguageThenItStartsInFrench() = runTest(UnconfinedTestDispatcher()) {
        val multiLanguageI18N = FlowMultiLanguageI18N(FR, languages)
        assertEquals(FR, multiLanguageI18N.getLanguageCode())
        assertEquals(frenchI18N, multiLanguageI18N.currentI18N)
        assertEquals(frenchI18N, multiLanguageI18N.i18N.first())
    }

    @Test
    fun givenFrenchInitialLanguageThenItForwardsCallToFrenchI18N() = runTest(UnconfinedTestDispatcher()) {
        val multiLanguageI18N = FlowMultiLanguageI18N(FR, languages)
        assertEquals(FR_VALUE, multiLanguageI18N[SomeKey].first())
        assertEquals(FR_VALUE, multiLanguageI18N.t(SomeKey).first())

        assertEquals("fr arg = value", multiLanguageI18N.t(ArgKey, "arg" to "value").first())
        assertEquals("fr arg = value", multiLanguageI18N.t(ArgKey, 2, "arg" to "value").first())
    }

    @Test
    fun givenLanguageSwitchedToEnglishThenItForwardsCallToEnglishI18N() = runTest(UnconfinedTestDispatcher()) {
        val multiLanguageI18N = FlowMultiLanguageI18N(FR, languages)
        multiLanguageI18N.changeLanguage(EN)

        assertEquals(EN_VALUE, multiLanguageI18N[SomeKey].first())
        assertEquals(EN_VALUE, multiLanguageI18N.t(SomeKey).first())

        assertEquals("en arg = value", multiLanguageI18N.t(ArgKey, "arg" to "value").first())
        assertEquals("en arg = value", multiLanguageI18N.t(ArgKey, 2, "arg" to "value").first())
    }

    @Test
    fun givenFrenchInitialLanguageWhenSwitchingToEnglishAndResettingThenItGoesBackToFrench() = runTest(UnconfinedTestDispatcher()) {
        val multiLanguageI18N = FlowMultiLanguageI18N(FR, languages)
        multiLanguageI18N.changeLanguage(EN)
        multiLanguageI18N.reset()
        assertEquals(FR, multiLanguageI18N.getLanguageCode())
    }

    private object SomeKey : KWordKey {
        override val translationKey = "SomeKey"
    }

    private object ArgKey : KWordKey {
        override val translationKey = "ArgKey"
    }
}
