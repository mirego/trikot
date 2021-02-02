package com.trikot.viewmodels.sample.viewmodels

import com.mirego.trikot.viewmodels.ListItemViewModel
import com.mirego.trikot.viewmodels.ToggleSwitchViewModel

interface ToggleSwitchListItemViewModel : ListItemViewModel {
    val toggleSwitch: ToggleSwitchViewModel
}
