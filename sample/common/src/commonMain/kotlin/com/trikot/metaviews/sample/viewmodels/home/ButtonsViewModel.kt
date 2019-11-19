package com.trikot.metaviews.sample.viewmodels.home

import com.mirego.trikot.metaviews.properties.Alignment
import com.mirego.trikot.metaviews.properties.Color
import com.mirego.trikot.metaviews.properties.MetaAction
import com.mirego.trikot.metaviews.properties.MetaSelector
import com.mirego.trikot.metaviews.resource.ImageResource
import com.mirego.trikot.metaviews.text.RichText
import com.mirego.trikot.metaviews.text.RichTextRange
import com.mirego.trikot.metaviews.text.StyleTransform
import com.mirego.trikot.streams.reactive.just
import com.trikot.metaviews.sample.metaviews.*
import com.trikot.metaviews.sample.navigation.NavigationDelegate
import com.trikot.metaviews.sample.resource.ImageResources

class ButtonsViewModel(navigationDelegate: NavigationDelegate): ListViewModel {
    override val items: List<MetaListItem> = listOf(
        MutableHeaderListItem(".backgroundColor (normal + highlighted)"),
        MutableMetaButtonListItem().also {
            it.button.onTap = MetaAction { }.just()
            it.button.backgroundColor = MetaSelector(Color(143, 143, 143), Color(255, 0, 0)).just()
        },
        MutableHeaderListItem(".text"),
        MutableMetaButtonListItem().also {
            it.button.text = "The text is here".just()
        },
        MutableHeaderListItem(".richText"),
        MutableMetaButtonListItem().also {
            it.button.richText = RichText("normal, bold, underlined, italic, bold italic",
                listOf(
                    RichTextRange(IntRange(0, 8), StyleTransform(StyleTransform.Style.NORMAL)),
                    RichTextRange(IntRange(8, 14), StyleTransform(StyleTransform.Style.BOLD)),
                    RichTextRange(IntRange(14, 26), StyleTransform(StyleTransform.Style.UNDERLINE)),
                    RichTextRange(IntRange(26, 34), StyleTransform(StyleTransform.Style.ITALIC)),
                    RichTextRange(IntRange(34, 45), StyleTransform(StyleTransform.Style.BOLD_ITALIC))
                )).just()
        },
        MutableHeaderListItem(".alpha"),
        MutableMetaButtonListItem().also {
            it.button.alpha = 0.5f.just()
            it.button.text = "I have 50% alpha".just()
        },
        MutableHeaderListItem(".hidden"),
        MutableMetaButtonListItem().also {
            it.button.hidden = true.just()
            it.button.text = "You shall not see me".just()
        },
        MutableHeaderListItem(".onTap"),
        MutableMetaButtonListItem().also {
            it.button.onTap = MetaAction { navigationDelegate.showAlert("Tapped $it") }.just()
            it.button.text = "Tap me".just()
        },
        MutableHeaderListItem(".enabled"),
        MutableMetaButtonListItem().also {
            it.button.enabled = false.just()
            it.button.text = "I am disabled".just()
        },
        MutableHeaderListItem(".selected"),
        MutableMetaButtonListItem().also {
            it.button.selected = true.just()
            it.button.text = "I am selected".just()
        },
        MutableHeaderListItem(".imageResource"),
        MutableMetaButtonListItem().also {
            it.button.imageResource = MetaSelector(ImageResources.ICON as ImageResource).just()
        },
        MutableHeaderListItem(".imageResource .imageAlignment"),
        MutableMetaButtonListItem().also {
            it.button.text = "Icon to the left".just()
            it.button.imageAlignment = Alignment.LEFT.just()
            it.button.imageResource = MetaSelector(ImageResources.ICON as ImageResource).just()
        },
        MutableHeaderListItem(".imageResource .imageAlignment"),
        MutableMetaButtonListItem().also {
            it.button.text = "Icon to the right".just()
            it.button.imageAlignment = Alignment.RIGHT.just()
            it.button.imageResource = MetaSelector(ImageResources.ICON as ImageResource).just()
        },
        MutableHeaderListItem(".textColor (normal + highlighted)"),
        MutableMetaButtonListItem().also {
            it.button.textColor = MetaSelector(Color(123, 123, 123), Color(255, 255, 255)).just()
            it.button.onTap = MetaAction {}.just()
            it.button.text = "I am gray".just()
        },
        MutableHeaderListItem(".imageResource .tintColor (normal + highlighted)"),
        MutableMetaButtonListItem().also {
            it.button.imageResource = MetaSelector(ImageResources.ICON as ImageResource).just()
            it.button.tintColor = MetaSelector(Color(255, 0, 0), Color(123, 123, 123)).just()
            it.button.onTap = MetaAction {}.just()
        }
    )
}
