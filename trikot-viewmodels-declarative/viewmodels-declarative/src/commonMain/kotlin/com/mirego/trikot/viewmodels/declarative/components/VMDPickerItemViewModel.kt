package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.viewmodels.declarative.content.VMDIdentifiableContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel

interface VMDPickerItemViewModel : VMDViewModel, VMDIdentifiableContent {
    var isEnabled: Boolean
}
