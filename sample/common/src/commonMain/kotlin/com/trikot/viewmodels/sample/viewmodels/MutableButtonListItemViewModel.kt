package com.trikot.viewmodels.sample.viewmodels

import com.mirego.trikot.viewmodels.ListItemViewModel
import com.mirego.trikot.viewmodels.mutable.MutableButtonViewModel
import com.mirego.trikot.viewmodels.mutable.MutableViewModel

class MutableButtonListItemViewModel(override var comparableId: String = "") : ButtonListItemViewModel, MutableViewModel() {
    override val button = MutableButtonViewModel()

    override fun isTheSame(other: ListItemViewModel): Boolean {
        return false
    }
}
