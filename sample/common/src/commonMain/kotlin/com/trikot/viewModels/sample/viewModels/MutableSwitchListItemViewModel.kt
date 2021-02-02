package com.trikot.viewmodels.sample.viewmodels

import com.mirego.trikot.viewmodels.ListItemViewModel
import com.mirego.trikot.viewmodels.mutable.MutableSwitchViewModel
import com.mirego.trikot.viewmodels.mutable.MutableViewModel

class MutableSwitchListItemViewModel(
    override var comparableId: String = ""
) : SwitchListItemViewModel, MutableViewModel() {
    override val switch = MutableSwitchViewModel()

    override fun isTheSame(other: ListItemViewModel): Boolean {
        return false
    }
}
