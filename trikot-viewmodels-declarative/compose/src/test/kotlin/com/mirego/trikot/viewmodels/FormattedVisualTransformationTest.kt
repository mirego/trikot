package com.mirego.trikot.viewmodels

import androidx.compose.ui.text.AnnotatedString
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.internal.FormattedVisualTransformation
import kotlin.test.Test
import kotlin.test.assertEquals

class FormattedVisualTransformationTest {

    @Test
    fun `Given original string "ABCDEFGHI" when transformed to "ABC DEF GHI" then returns the correct mapping positions`() {
        val formattedVisualTransformation = FormattedVisualTransformation { text: String -> text.chunked(3).joinToString(" ") }
        val annotatedString = AnnotatedString("ABCDEFGHI")
        val filter = formattedVisualTransformation.filter(annotatedString)

        assertEquals(0, filter.offsetMapping.transformedToOriginal(0))
        assertEquals(1, filter.offsetMapping.transformedToOriginal(1))
        assertEquals(2, filter.offsetMapping.transformedToOriginal(2))
        assertEquals(3, filter.offsetMapping.transformedToOriginal(3))
        assertEquals(3, filter.offsetMapping.transformedToOriginal(4))
        assertEquals(4, filter.offsetMapping.transformedToOriginal(5))
        assertEquals(5, filter.offsetMapping.transformedToOriginal(6))
        assertEquals(6, filter.offsetMapping.transformedToOriginal(7))
        assertEquals(6, filter.offsetMapping.transformedToOriginal(8))
        assertEquals(7, filter.offsetMapping.transformedToOriginal(9))
        assertEquals(8, filter.offsetMapping.transformedToOriginal(10))
        assertEquals(9, filter.offsetMapping.transformedToOriginal(11))

        assertEquals(0, filter.offsetMapping.originalToTransformed(0))
        assertEquals(1, filter.offsetMapping.originalToTransformed(1))
        assertEquals(2, filter.offsetMapping.originalToTransformed(2))
        assertEquals(4, filter.offsetMapping.originalToTransformed(3))
        assertEquals(5, filter.offsetMapping.originalToTransformed(4))
        assertEquals(6, filter.offsetMapping.originalToTransformed(5))
        assertEquals(8, filter.offsetMapping.originalToTransformed(6))
        assertEquals(9, filter.offsetMapping.originalToTransformed(7))
        assertEquals(10, filter.offsetMapping.originalToTransformed(8))
        assertEquals(11, filter.offsetMapping.originalToTransformed(9))
    }

    @Test
    fun `Given password string when transformed to * then returns the correct mapping positions`() {
        val formattedVisualTransformation = FormattedVisualTransformation { text: String -> "*".repeat(text.length) }
        val annotatedString = AnnotatedString("password")
        val filter = formattedVisualTransformation.filter(annotatedString)

        for (i in annotatedString.text.indices) {
            assertEquals(i, filter.offsetMapping.transformedToOriginal(i))
            assertEquals(i, filter.offsetMapping.originalToTransformed(i))
        }
    }

    @Test
    fun `When text is empty string then returns the correct mapping positions`() {
        val formattedVisualTransformation = FormattedVisualTransformation { text: String -> text }
        val annotatedString = AnnotatedString("")
        val filter = formattedVisualTransformation.filter(annotatedString)

        assertEquals(0, filter.offsetMapping.transformedToOriginal(0))
        assertEquals(0, filter.offsetMapping.originalToTransformed(0))
    }
}
