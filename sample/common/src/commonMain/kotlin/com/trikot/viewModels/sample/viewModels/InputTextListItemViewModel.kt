package com.trikot.viewmodels.sample.viewmodels

import com.mirego.trikot.viewmodels.InputTextViewModel
import com.mirego.trikot.viewmodels.ListItemViewModel

interface InputTextListItemViewModel : ListItemViewModel {
    val inputText: InputTextViewModel
}
