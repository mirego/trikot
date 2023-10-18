package com.mirego.sample.viewmodels.showcase.components.picker

import com.mirego.sample.KWordTranslation
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.map
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDContentPickerItemViewModelImpl
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.picker
import com.mirego.trikot.viewmodels.declarative.viewmodel.text

class PickerShowcaseViewModelImpl(i18N: I18N, cancellableManager: CancellableManager) :
    ShowcaseViewModelImpl(cancellableManager), PickerShowcaseViewModel {
    override val title = VMDComponents.Text.withContent(
        i18N[KWordTranslation.PICKER_SHOWCASE_TITLE],
        cancellableManager
    )

    override val textPicker = VMDComponents.Picker.withElements(
        cancellableManager,
        listOf(
            VMDContentPickerItemViewModelImpl(cancellableManager, VMDTextContent("Item 1"), "item_1"),
            VMDContentPickerItemViewModelImpl(cancellableManager, VMDTextContent("Item 2"), "item_2"),
            VMDContentPickerItemViewModelImpl(cancellableManager, VMDTextContent("Item 3"), "item_3")
        )
    )
    override val textPicker2 = picker(
        listOf(
            VMDContentPickerItemViewModelImpl(cancellableManager, VMDTextContent("Item 1"), "item_1"),
            VMDContentPickerItemViewModelImpl(cancellableManager, VMDTextContent("Item 2"), "item_2"),
            VMDContentPickerItemViewModelImpl(cancellableManager, VMDTextContent("Item 3"), "item_3")
        )
    )

    override val textPickerTitle = VMDComponents.Text.withContent(
        i18N[KWordTranslation.PICKER_SHOWCASE_TEXT],
        cancellableManager
    ) {
        val initialValue = text
        bindText(
            textPicker.publisherForProperty(textPicker::selectedIndex).map { index ->
                textPicker.elements.getOrNull(index)?.content?.text ?: initialValue
            }
        )
    }

    override val textPickerTitle2 = text(i18N[KWordTranslation.PICKER_SHOWCASE_TEXT]) {
        val initialValue = text
        bindText(
            textPicker2.publisherForProperty(textPicker::selectedIndex).map { index ->
                textPicker2.elements.getOrNull(index)?.content?.text ?: initialValue
            }
        )
    }
}
