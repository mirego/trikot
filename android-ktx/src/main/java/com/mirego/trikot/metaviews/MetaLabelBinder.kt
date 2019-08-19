package com.mirego.trikot.metaviews

import android.graphics.Typeface
import android.text.ParcelableSpan
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.mirego.trikot.metaviews.mutable.MutableMetaLabel
import com.mirego.trikot.metaviews.text.RichTextRange
import com.mirego.trikot.metaviews.text.StyleTransform
import com.mirego.trikot.streams.android.ktx.observe

object MetaLabelBinder {

    private val NoMetaLabel = MutableMetaLabel().apply { hidden.value = true } as MetaLabel

    @JvmStatic
    @BindingAdapter("meta_view", "hiddenVisibility", "lifecycleOwnerWrapper")
    fun bind(
        textView: TextView,
        metaLabel: MetaLabel?,
        hiddenVisibility: HiddenVisibility,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        val label = metaLabel ?: NoMetaLabel

        label.richText?.observe(lifecycleOwnerWrapper.lifecycleOwner) { richText ->
            textView.text = SpannableString(richText.text).apply {
                richText.ranges.forEach {
                    setSpan(
                        it.asSpan(),
                        it.range.first,
                        it.range.last,
                        Spanned.SPAN_EXCLUSIVE_INCLUSIVE
                    )
                }
            }
        }

        label.takeUnless { it.richText != null }?.text
            ?.observe(lifecycleOwnerWrapper.lifecycleOwner) {
                textView.text = it
            }

        bindExtraViewProperties(textView, label, hiddenVisibility, lifecycleOwnerWrapper)
    }

    @JvmStatic
    @BindingAdapter("meta_view", "lifecycleOwnerWrapper")
    fun bind(
        textView: TextView,
        metaLabel: MetaLabel?,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        bind(textView, metaLabel, HiddenVisibility.GONE, lifecycleOwnerWrapper)
    }

    @JvmStatic
    fun bindWithoutTextPublishers(
        textView: TextView,
        metaLabel: MetaLabel?,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        bindExtraViewProperties(
            textView, metaLabel ?: NoMetaLabel, HiddenVisibility.GONE, lifecycleOwnerWrapper
        )
    }

    @JvmStatic
    private fun bindExtraViewProperties(
        textView: TextView,
        metaLabel: MetaLabel,
        hiddenVisibility: HiddenVisibility,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        metaLabel.hidden.observe(lifecycleOwnerWrapper.lifecycleOwner) { hidden ->
            with(textView) { visibility = if (hidden) hiddenVisibility.value else View.VISIBLE }
        }

        textView.bindOnTap(metaLabel, lifecycleOwnerWrapper)
    }
}

enum class HiddenVisibility(val value: Int) {
    GONE(View.GONE),
    INVISIBLE(View.INVISIBLE);
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
        else -> TODO("RichTextRange $transform not implemented")
    }
}
