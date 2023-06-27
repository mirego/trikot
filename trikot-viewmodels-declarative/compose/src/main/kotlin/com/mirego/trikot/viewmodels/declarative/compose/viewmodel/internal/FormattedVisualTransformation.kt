package com.mirego.trikot.viewmodels.declarative.compose.viewmodel.internal

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

internal class FormattedVisualTransformation(private val formatter: (text: String) -> String) :
    VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text
        val formattedString = formatter(originalText)
        val offsetDelta = formattedString.length - text.length

        val offsetMapping: OffsetMapping
        if (offsetDelta > 0) {
            val transformedToOriginalMapping = Array(formattedString.length) { -1 }
            // First we fill mapping from original string
            for (i in originalText.indices) {
                transformedToOriginalMapping[formatter(originalText.substring(0, i + 1)).length - 1] = i
            }
            // Then we fills the missing indices to advance to the next offset
            var nextOffset = originalText.length - 1
            for (offset in transformedToOriginalMapping.indices.reversed()) {
                if (transformedToOriginalMapping[offset] == -1) {
                    transformedToOriginalMapping[offset] = nextOffset
                } else {
                    nextOffset = transformedToOriginalMapping[offset]
                }
            }

            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return offset + lengthDelta(offset)
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return if (offset < transformedToOriginalMapping.size) {
                        transformedToOriginalMapping[offset]
                    } else {
                        originalText.length
                    }
                }

                private fun lengthDelta(offset: Int): Int {
                    val originalSubstring = originalText.substring(0, Integer.min(offset + 1, originalText.length))
                    val formattedSubstring = formatter(originalSubstring)
                    return formattedSubstring.length - originalSubstring.length
                }
            }
        } else {
            offsetMapping = OffsetMapping.Identity
        }

        return TransformedText(
            text = AnnotatedString(formattedString),
            offsetMapping = offsetMapping
        )
    }
}
