package com.trikot.viewmodels.sample.viewmodels

import com.mirego.trikot.viewmodels.ButtonViewModel
import com.mirego.trikot.viewmodels.ListItemViewModel

interface ButtonListItemViewModel : ListItemViewModel {
    val button: ButtonViewModel
}
