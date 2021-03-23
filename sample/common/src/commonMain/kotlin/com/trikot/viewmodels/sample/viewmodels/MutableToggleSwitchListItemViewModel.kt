package com.trikot.viewmodels.sample.viewmodels

import com.mirego.trikot.viewmodels.ListItemViewModel
import com.mirego.trikot.viewmodels.mutable.MutableToggleSwitchViewModel
import com.mirego.trikot.viewmodels.mutable.MutableViewModel

class MutableToggleSwitchListItemViewModel(
    override var comparableId: String = ""
) : ToggleSwitchListItemViewModel, MutableViewModel() {
    override val toggleSwitch = MutableToggleSwitchViewModel()

    override fun isTheSame(other: ListItemViewModel): Boolean {
        return false
    }
}
