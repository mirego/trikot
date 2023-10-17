package com.mirego.sample.viewmodels.showcase.components.picker

import com.mirego.sample.KWordTranslation
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.components.VMDPickerViewModel
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDContentPickerItemViewModelImpl
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.picker
import com.mirego.trikot.viewmodels.declarative.viewmodel.text
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map

class PickerShowcaseViewModelImpl(i18N: I18N, coroutineScope: CoroutineScope) :
    ShowcaseViewModelImpl(coroutineScope), PickerShowcaseViewModel {
    override val title = text(i18N[KWordTranslation.PICKER_SHOWCASE_TITLE])

    override val textPicker = picker(
        listOf(
            VMDContentPickerItemViewModelImpl(coroutineScope, VMDTextContent("Item 1"), "item_1"),
            VMDContentPickerItemViewModelImpl(coroutineScope, VMDTextContent("Item 2"), "item_2"),
            VMDContentPickerItemViewModelImpl(coroutineScope, VMDTextContent("Item 3"), "item_3")
        )
    )
    override val textPicker2 = picker(
        listOf(
            VMDContentPickerItemViewModelImpl(coroutineScope, VMDTextContent("Item 1"), "item_1"),
            VMDContentPickerItemViewModelImpl(coroutineScope, VMDTextContent("Item 2"), "item_2"),
            VMDContentPickerItemViewModelImpl(coroutineScope, VMDTextContent("Item 3"), "item_3")
        )
    )

    override val textPickerTitle = text(i18N[KWordTranslation.PICKER_SHOWCASE_TEXT]) {
        val initialValue = text
        bindText(
            textPicker.flowForProperty(textPicker::selectedIndex).map { index ->
                textPicker.elements.getOrNull(index)?.content?.text ?: initialValue
            }
        )
    }

    override val textPickerTitle2 = text(i18N[KWordTranslation.PICKER_SHOWCASE_TEXT]) {
        val initialValue = text
        bindText(
            textPicker2.flowForProperty(textPicker::selectedIndex).map { index ->
                textPicker2.elements.getOrNull(index)?.content?.text ?: initialValue
            }
        )
    }
}
