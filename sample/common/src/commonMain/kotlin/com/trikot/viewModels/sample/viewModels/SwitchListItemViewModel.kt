package com.trikot.viewmodels.sample.viewmodels

import com.mirego.trikot.viewmodels.ListItemViewModel
import com.mirego.trikot.viewmodels.SwitchViewModel

interface SwitchListItemViewModel : ListItemViewModel {
    val switch: SwitchViewModel
}
