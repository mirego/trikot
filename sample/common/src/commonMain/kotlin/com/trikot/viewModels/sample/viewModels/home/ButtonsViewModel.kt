package com.trikot.viewmodels.sample.viewmodels.home

import com.mirego.trikot.viewmodels.properties.Alignment
import com.mirego.trikot.viewmodels.properties.Color
import com.mirego.trikot.viewmodels.properties.ViewModelAction
import com.mirego.trikot.viewmodels.properties.StateSelector
import com.mirego.trikot.viewmodels.resource.ImageResource
import com.mirego.trikot.viewmodels.text.RichText
import com.mirego.trikot.viewmodels.text.RichTextRange
import com.mirego.trikot.viewmodels.text.StyleTransform
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.viewmodels.ListItemViewModel
import com.mirego.trikot.viewmodels.mutable.MutableListViewModel
import com.trikot.viewmodels.sample.viewmodels.*
import com.trikot.viewmodels.sample.navigation.NavigationDelegate
import com.trikot.viewmodels.sample.resource.ImageResources
import org.reactivestreams.Publisher

class ButtonsViewModel(navigationDelegate: NavigationDelegate) : MutableListViewModel<ListItemViewModel>() {
    override var elements: Publisher<List<ListItemViewModel>> = listOf<ListItemViewModel>(
        MutableHeaderListItemViewModel(".backgroundColor (normal + highlighted)"),
        MutableButtonListItemViewModel().also {
            it.button.action = ViewModelAction { }.just()
            it.button.backgroundColor = StateSelector(Color(143, 143, 143), Color(255, 0, 0)).just()
        },
        MutableHeaderListItemViewModel(".text"),
        MutableButtonListItemViewModel().also {
            it.button.text = "The text is here".just()
        },
        MutableHeaderListItemViewModel(".richText"),
        MutableButtonListItemViewModel().also {
            it.button.richText = RichText("normal, bold, underlined, italic, bold italic",
                listOf(
                    RichTextRange(0..8, StyleTransform(StyleTransform.Style.NORMAL)),
                    RichTextRange(8..14, StyleTransform(StyleTransform.Style.BOLD)),
                    RichTextRange(14..26, StyleTransform(StyleTransform.Style.UNDERLINE)),
                    RichTextRange(26..34, StyleTransform(StyleTransform.Style.ITALIC)),
                    RichTextRange(34..45, StyleTransform(StyleTransform.Style.BOLD_ITALIC))
                )).just()
        },
        MutableHeaderListItemViewModel(".richText with color"),
        MutableButtonListItemViewModel().also {
            it.button.textColor = StateSelector(Color(123, 123, 123), Color(0, 0, 0)).just()
            it.button.richText = RichText("normal, bold, underlined, italic, bold italic",
                listOf(
                    RichTextRange(IntRange(0, 8), StyleTransform(StyleTransform.Style.NORMAL)),
                    RichTextRange(IntRange(8, 14), StyleTransform(StyleTransform.Style.BOLD)),
                    RichTextRange(IntRange(14, 26), StyleTransform(StyleTransform.Style.UNDERLINE)),
                    RichTextRange(IntRange(26, 34), StyleTransform(StyleTransform.Style.ITALIC)),
                    RichTextRange(IntRange(34, 45), StyleTransform(StyleTransform.Style.BOLD_ITALIC))
                )).just()
        },
        MutableHeaderListItemViewModel(".alpha"),
        MutableButtonListItemViewModel().also {
            it.button.alpha = 0.5f.just()
            it.button.text = "I have 50% alpha".just()
        },
        MutableHeaderListItemViewModel(".hidden"),
        MutableButtonListItemViewModel().also {
            it.button.hidden = true.just()
            it.button.text = "You shall not see me".just()
        },
        MutableHeaderListItemViewModel(".onTap"),
        MutableButtonListItemViewModel().also {
            it.button.action = ViewModelAction { navigationDelegate.showAlert("Tapped $it") }.just()
            it.button.text = "Tap me".just()
        },
        MutableHeaderListItemViewModel(".enabled"),
        MutableButtonListItemViewModel().also {
            it.button.enabled = false.just()
            it.button.text = "I am disabled".just()
        },
        MutableHeaderListItemViewModel(".selected"),
        MutableButtonListItemViewModel().also {
            it.button.selected = true.just()
            it.button.text = "I am selected".just()
        },
        MutableHeaderListItemViewModel(".imageResource"),
        MutableButtonListItemViewModel().also {
            it.button.imageResource = StateSelector(ImageResources.ICON as ImageResource).just()
        },
        MutableHeaderListItemViewModel(".imageResource .imageAlignment"),
        MutableButtonListItemViewModel().also {
            it.button.text = "Icon to the left".just()
            it.button.imageAlignment = Alignment.LEFT.just()
            it.button.imageResource = StateSelector(ImageResources.ICON as ImageResource).just()
        },
        MutableHeaderListItemViewModel(".imageResource .imageAlignment"),
        MutableButtonListItemViewModel().also {
            it.button.text = "Icon to the right".just()
            it.button.imageAlignment = Alignment.RIGHT.just()
            it.button.imageResource = StateSelector(ImageResources.ICON as ImageResource).just()
        },
        MutableHeaderListItemViewModel(".textColor (normal + highlighted)"),
        MutableButtonListItemViewModel().also {
            it.button.textColor = StateSelector(Color(123, 123, 123), Color(255, 255, 255)).just()
            it.button.action = ViewModelAction {}.just()
            it.button.text = "I am gray".just()
        },
        MutableHeaderListItemViewModel(".imageResource .tintColor (normal + highlighted)"),
        MutableButtonListItemViewModel().also {
            it.button.imageResource = StateSelector(ImageResources.ICON as ImageResource).just()
            it.button.tintColor = StateSelector(Color(255, 0, 0), Color(123, 123, 123)).just()
            it.button.action = ViewModelAction {}.just()
        }
    ).just()
}
