package com.mirego.sample.viewmodels.showcase.components.picker

import com.mirego.sample.viewmodels.showcase.ShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDPickerViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDContentPickerItemViewModelImpl
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent

interface PickerShowcaseViewModel : ShowcaseViewModel {
    val textPickerTitle: VMDTextViewModel
    val textPicker: VMDPickerViewModel<VMDContentPickerItemViewModelImpl<VMDTextContent>>
}