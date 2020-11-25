package com.mirego.trikot.foundation.strings

import kotlin.test.Test
import kotlin.test.assertEquals

class NativeStringExtensionsTest {
    @Test
    fun testNormalize() {
        val cue = "\u00C7\u00FA\u00EA"
        assertEquals("Cue", cue.normalize(), "Failed to strip diacritics from $cue")

        val lots = "\u00C0\u00C1\u00C2\u00C3\u00C4\u00C5\u00C7\u00C8\u00C9" +
                "\u00CA\u00CB\u00CC\u00CD\u00CE\u00CF\u00D1\u00D2\u00D3" +
                "\u00D4\u00D5\u00D6\u00D9\u00DA\u00DB\u00DC\u00DD"
        assertEquals(
            "AAAAAACEEEEIIIINOOOOOUUUUY",
            lots.normalize(),
            "Failed to strip diacritics from $lots"
        )
        assertEquals("", "".normalize(), "Failed empty String")
        assertEquals("control", "control".normalize(), "Failed to handle non-accented text")
        assertEquals("eclair", "\u00E9clair".normalize(), "Failed to handle easy example")
    }
}
