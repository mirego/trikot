package com.trikot.viewmodels.sample.viewmodels.home

import com.mirego.trikot.foundation.ref.weakAtomicReference
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.viewmodels.ListItemViewModel
import com.mirego.trikot.viewmodels.mutable.MutableListViewModel
import com.mirego.trikot.viewmodels.properties.Color
import com.mirego.trikot.viewmodels.properties.StateSelector
import com.mirego.trikot.viewmodels.properties.ViewModelAction
import com.mirego.trikot.viewmodels.text.RichText
import com.mirego.trikot.viewmodels.text.RichTextRange
import com.mirego.trikot.viewmodels.text.StyleTransform
import com.mirego.trikot.viewmodels.text.TextAppearanceResourceTransform
import com.trikot.viewmodels.sample.navigation.NavigationDelegate
import com.trikot.viewmodels.sample.resource.SampleTextAppearanceResource
import com.trikot.viewmodels.sample.viewmodels.MutableHeaderListItemViewModel
import com.trikot.viewmodels.sample.viewmodels.MutableLabelListItemViewModel
import org.reactivestreams.Publisher

class LabelsViewModel :
    MutableListViewModel<ListItemViewModel>() {

    var navigationDelegate: NavigationDelegate? by weakAtomicReference()

    override var elements: Publisher<List<ListItemViewModel>> = listOf<ListItemViewModel>(
        MutableHeaderListItemViewModel(".backgroundColor"),
        MutableLabelListItemViewModel().also {
            it.label.text = "Text on red background".just()
            it.label.backgroundColor = StateSelector(Color(255, 0, 0)).just()
        },
        MutableHeaderListItemViewModel(".alpha"),
        MutableLabelListItemViewModel().also {
            it.label.alpha = 0.5f.just()
            it.label.text = "I have 50% alpha".just()
        },
        MutableHeaderListItemViewModel(".hidden"),
        MutableLabelListItemViewModel().also {
            it.label.hidden = true.just()
            it.label.text = "You shall not see me".just()
        },
        MutableHeaderListItemViewModel(".onTap"),
        MutableLabelListItemViewModel().also {
            it.label.action = ViewModelAction { navigationDelegate?.showAlert("Tapped $it") }.just()
            it.label.text = "Tap me".just()
        },
        MutableHeaderListItemViewModel(".text"),
        MutableLabelListItemViewModel().also {
            it.label.text = "The text is here".just()
        },
        MutableHeaderListItemViewModel(".richText"),
        MutableLabelListItemViewModel().also {
            it.label.richText = RichText(
                "normal, bold, underlined, italic, bold italic",
                listOf(
                    RichTextRange(IntRange(0, 8), StyleTransform(StyleTransform.Style.NORMAL)),
                    RichTextRange(IntRange(8, 14), StyleTransform(StyleTransform.Style.BOLD)),
                    RichTextRange(IntRange(14, 26), StyleTransform(StyleTransform.Style.UNDERLINE)),
                    RichTextRange(IntRange(26, 34), StyleTransform(StyleTransform.Style.ITALIC)),
                    RichTextRange(
                        IntRange(34, 45),
                        StyleTransform(StyleTransform.Style.BOLD_ITALIC)
                    )
                )
            ).just()
        },
        MutableHeaderListItemViewModel(".textColor"),
        MutableLabelListItemViewModel().also {
            it.label.action = ViewModelAction { navigationDelegate?.showAlert("Tapped $it") }.just()
            it.label.text = "I am red".just()
            it.label.textColor = StateSelector(Color(255, 0, 0)).just()
        },
        MutableHeaderListItemViewModel(".richTextTransform"),
        MutableLabelListItemViewModel().also {
            it.label.richText = RichText(
                "Text appearance defined by the Client App Theme. Superscript example: 63",
                listOf(
                    RichTextRange(
                        IntRange(0, 10),
                        TextAppearanceResourceTransform(SampleTextAppearanceResource.TEXT_APPEARANCE_BOLD)
                    ),
                    RichTextRange(
                        IntRange(10, 20),
                        TextAppearanceResourceTransform(SampleTextAppearanceResource.TEXT_APPEARANCE_COLORED)
                    ),
                    RichTextRange(
                        IntRange(20, 30),
                        TextAppearanceResourceTransform(SampleTextAppearanceResource.TEXT_APPEARANCE_ITALIC)
                    ),
                    RichTextRange(
                        IntRange(30, 38),
                        TextAppearanceResourceTransform(SampleTextAppearanceResource.TEXT_APPEARANCE_GRAYED)
                    ),
                    RichTextRange(
                        IntRange(38, 48),
                        TextAppearanceResourceTransform(SampleTextAppearanceResource.TEXT_APPEARANCE_HIGHLIGHTED)
                    ),
                    RichTextRange(
                        IntRange(49, 70),
                        TextAppearanceResourceTransform(SampleTextAppearanceResource.TEXT_APPEARANCE_ITALIC)
                    ),
                    RichTextRange(
                        IntRange(71, 72),
                        TextAppearanceResourceTransform(SampleTextAppearanceResource.TEXT_APPEARANCE_COLORED)
                    ),
                    RichTextRange(
                        IntRange(70, 72),
                        TextAppearanceResourceTransform(SampleTextAppearanceResource.TEXT_APPEARANCE_SUPERSCRIPT)
                    )
                )
            ).just()
        },
        MutableHeaderListItemViewModel(".accessibilityLabel (\"This is an accessible label\")"),
        MutableLabelListItemViewModel().also {
            it.label.accessibilityLabel = "This is an accessible label".just()
            it.label.text = "I am accessible".just()
        },
    ).just()
}
