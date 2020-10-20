package com.mirego.trikot.kword

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PluralsTests {
    private val simpleKey = object : KWordKey { override val translationKey = "simpleKey" }
    private val argumentKey = object : KWordKey { override val translationKey = "argumentKey" }
    private val defaultKey = object : KWordKey { override val translationKey = "zero_one_many" }
    private val zeroKey = object : KWordKey { override val translationKey = "zero_one_many_0" }
    private val threeKey = object : KWordKey { override val translationKey = "zero_one_many_3" }
    private val oneKey = object : KWordKey { override val translationKey = "zero_one_many_1" }

    private val expectedArgument = "ARGS!"

    private val strings = mapOf(
        simpleKey.translationKey to simpleKey.translationKey,
        argumentKey.translationKey to "${argumentKey.translationKey} {{argument}}",
        zeroKey.translationKey to "${zeroKey.translationKey} {{argument}} {{count}}",
        oneKey.translationKey to "${oneKey.translationKey} {{argument}} {{count}}",
        threeKey.translationKey to "${threeKey.translationKey} {{argument}} {{count}}",
        defaultKey.translationKey to "${defaultKey.translationKey} {{argument}} {{count}}"
    )

    private val defaultI18N = DefaultI18N()

    @BeforeTest
    fun setup() {
        defaultI18N.changeLocaleStrings(strings)
    }

    @Test
    fun noArgumentTests() {
        assertEquals(simpleKey.translationKey, defaultI18N.t(simpleKey))
    }

    @Test
    fun argumentTests() {
        val translated = defaultI18N.t(argumentKey, "argument" to expectedArgument)
        assertEquals("argumentKey $expectedArgument", translated)
    }

    @Test
    fun zeroOneManyTest() {
        val results = listOf(0, 1, 2, 3).map { defaultI18N.t(defaultKey, it, "argument" to expectedArgument) }

        assertEquals("${zeroKey.translationKey} $expectedArgument 0", results[0])
        assertEquals("${oneKey.translationKey} $expectedArgument 1", results[1])
        assertEquals("${defaultKey.translationKey} $expectedArgument 2", results[2])
        assertEquals("${threeKey.translationKey} $expectedArgument 3", results[3])
    }
}
