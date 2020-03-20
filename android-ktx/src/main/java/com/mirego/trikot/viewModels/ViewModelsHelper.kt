package com.mirego.trikot.viewmodels

import android.graphics.Typeface
import android.text.ParcelableSpan
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import com.mirego.trikot.viewmodels.text.ColorTransform
import com.mirego.trikot.viewmodels.text.RichText
import com.mirego.trikot.viewmodels.text.RichTextRange
import com.mirego.trikot.viewmodels.text.StyleTransform

fun RichText.asSpannableString(): SpannableString {
    return SpannableString(text).apply {
        ranges.forEach {
            setSpan(
                it.asSpan(),
                it.range.first,
                it.range.last,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }
    }
}

private fun RichTextRange.asSpan(): ParcelableSpan {
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
        else -> TODO("RichTextRange $transform not implemented")
    }
}

