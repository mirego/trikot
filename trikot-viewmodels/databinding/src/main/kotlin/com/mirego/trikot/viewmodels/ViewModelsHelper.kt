package com.mirego.trikot.viewmodels

import android.content.Context
import android.graphics.Typeface
import android.text.ParcelableSpan
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import com.mirego.trikot.viewmodels.resources.TextAppearanceSpanResourceManager
import com.mirego.trikot.viewmodels.text.ColorTransform
import com.mirego.trikot.viewmodels.text.RichText
import com.mirego.trikot.viewmodels.text.RichTextRange
import com.mirego.trikot.viewmodels.text.StyleTransform
import com.mirego.trikot.viewmodels.text.TextAppearanceResourceTransform

fun RichText.asSpannableString(context: Context): SpannableString {
    return SpannableString(text).apply {
        ranges.forEach { range ->
            range.asSpan(context)?.let {
                setSpan(
                    it,
                    range.range.first,
                    range.range.last,
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE
                )
            }
        }
    }
}

private fun RichTextRange.asSpan(context: Context): ParcelableSpan? {
    return when (transform) {
        is StyleTransform -> {
            when ((transform as StyleTransform).style) {
                StyleTransform.Style.NORMAL -> StyleSpan(Typeface.NORMAL)
                StyleTransform.Style.BOLD -> StyleSpan(Typeface.BOLD)
                StyleTransform.Style.ITALIC -> StyleSpan(Typeface.ITALIC)
                StyleTransform.Style.UNDERLINE -> UnderlineSpan()
                StyleTransform.Style.BOLD_ITALIC -> StyleSpan(Typeface.BOLD_ITALIC)
            }
        }
        is ColorTransform -> {
            ForegroundColorSpan((transform as ColorTransform).color.toIntColor())
        }
        is TextAppearanceResourceTransform -> {
            TextAppearanceSpanResourceManager.provider.spanFromResource(
                (transform as? TextAppearanceResourceTransform)?.textAppearanceResource
                    ?: throw IllegalArgumentException("transform is not an instance of TextAppearanceResourceTransform"),
                context
            )
        }
    }
}
