package com.trikot.viewmodels.sample.viewmodels

import com.mirego.trikot.viewmodels.ListItemViewModel
import com.mirego.trikot.viewmodels.PickerViewModel

interface PickerListItemViewModel : ListItemViewModel {
    val picker: PickerViewModel<String>
}
