package com.mirego.trikot.metaviews.text

import com.mirego.trikot.metaviews.resource.Font

sealed class RichTextTransform {
    class FontTransform(val font: Font) : RichTextTransform()
    class StyleTransform(val style: Style) {
        enum class Style {
            NORMAL,
            BOLD,
            ITALIC,
            UNDERLINE
        }
    }
}
