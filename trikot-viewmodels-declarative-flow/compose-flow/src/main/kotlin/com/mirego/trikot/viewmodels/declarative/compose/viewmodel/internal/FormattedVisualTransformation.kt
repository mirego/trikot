package com.mirego.trikot.viewmodels.declarative.compose.viewmodel.internal

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

internal class FormattedVisualTransformation(private val formatter: (text: String) -> String) :
    VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val formattedString = formatter(text.text)
        val offsetDelta = formattedString.length - text.length
        return TransformedText(
            text = AnnotatedString(formattedString),
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return offset + offsetDelta
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return offset - offsetDelta
                }
            }
        )
    }
}