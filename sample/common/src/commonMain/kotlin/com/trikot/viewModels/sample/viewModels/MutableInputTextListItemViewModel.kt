package com.trikot.viewmodels.sample.viewmodels

import com.mirego.trikot.viewmodels.mutable.MutableInputTextViewModel
import com.mirego.trikot.viewmodels.mutable.MutableViewModel

class MutableInputTextListItemViewModel(override var comparableId: String = "") : InputTextListItemViewModel, MutableViewModel() {
    override val inputText = MutableInputTextViewModel()

    override fun isTheSame(other: ListItemViewModel): Boolean {
        return false
    }
}
