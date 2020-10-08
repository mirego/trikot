package com.mirego.trikot.viewmodels.text

import com.mirego.trikot.viewmodels.properties.Color
import com.mirego.trikot.viewmodels.resource.TextAppearanceResource

data class RichText(val text: String, val ranges: List<RichTextRange>)

data class RichTextRange(val range: IntRange, val transform: RichTextTransform)

sealed class RichTextTransform

data class StyleTransform(val style: Style) : RichTextTransform() {
    enum class Style {
        NORMAL,
        BOLD,
        ITALIC,
        BOLD_ITALIC,
        UNDERLINE
    }
}

data class ColorTransform(val color: Color) : RichTextTransform()

data class TextAppearanceResourceTransform(val textAppearanceResource: TextAppearanceResource) :
    RichTextTransform()
