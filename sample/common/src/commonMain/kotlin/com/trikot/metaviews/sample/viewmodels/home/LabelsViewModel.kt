package com.trikot.metaviews.sample.viewmodels.home

import com.mirego.trikot.metaviews.properties.Color
import com.mirego.trikot.metaviews.properties.MetaAction
import com.mirego.trikot.metaviews.properties.MetaSelector
import com.mirego.trikot.metaviews.text.RichText
import com.mirego.trikot.metaviews.text.RichTextRange
import com.mirego.trikot.metaviews.text.StyleTransform
import com.mirego.trikot.streams.reactive.just
import com.trikot.metaviews.sample.metaviews.MetaListItem
import com.trikot.metaviews.sample.metaviews.MutableHeaderListItem
import com.trikot.metaviews.sample.metaviews.MutableMetaLabelListItem
import com.trikot.metaviews.sample.navigation.NavigationDelegate

class LabelsViewModel(navigationDelegate: NavigationDelegate) : ListViewModel {
    override val items: List<MetaListItem> = listOf(
        MutableHeaderListItem(".backgroundColor"),
        MutableMetaLabelListItem().also {
            it.label.text = "Text on red background".just()
            it.label.backgroundColor = MetaSelector(Color(255, 0, 0)).just()
        },
        MutableHeaderListItem(".alpha"),
        MutableMetaLabelListItem().also {
            it.label.alpha = 0.5f.just()
            it.label.text = "I have 50% alpha".just()
        },
        MutableHeaderListItem(".hidden"),
        MutableMetaLabelListItem().also {
            it.label.hidden = true.just()
            it.label.text = "You shall not see me".just()
        },
        MutableHeaderListItem(".onTap"),
        MutableMetaLabelListItem().also {
            it.label.onTap = MetaAction { navigationDelegate.showAlert("Tapped $it") }.just()
            it.label.text = "Tap me".just()
        },
        MutableHeaderListItem(".text"),
        MutableMetaLabelListItem().also {
            it.label.text = "The text is here".just()
        },
        MutableHeaderListItem(".richText"),
        MutableMetaLabelListItem().also {
            it.label.richText = RichText("normal, bold, underlined, italic, bold italic",
                listOf(
                    RichTextRange(IntRange(0, 8), StyleTransform(StyleTransform.Style.NORMAL)),
                    RichTextRange(IntRange(8, 14), StyleTransform(StyleTransform.Style.BOLD)),
                    RichTextRange(IntRange(14, 26), StyleTransform(StyleTransform.Style.UNDERLINE)),
                    RichTextRange(IntRange(26, 34), StyleTransform(StyleTransform.Style.ITALIC)),
                    RichTextRange(IntRange(34, 45), StyleTransform(StyleTransform.Style.BOLD_ITALIC))
                )).just()
        },
        MutableHeaderListItem(".textColor"),
        MutableMetaLabelListItem().also {
            it.label.onTap = MetaAction { navigationDelegate.showAlert("Tapped $it") }.just()
            it.label.text = "I am red".just()
            it.label.textColor = MetaSelector(Color(255, 0, 0)).just()
        }
    )
}
