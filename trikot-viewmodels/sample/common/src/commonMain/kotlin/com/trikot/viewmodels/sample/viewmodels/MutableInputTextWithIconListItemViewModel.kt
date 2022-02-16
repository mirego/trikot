package com.trikot.viewmodels.sample.viewmodels

import com.mirego.trikot.viewmodels.ListItemViewModel
import com.mirego.trikot.viewmodels.mutable.MutableInputTextWithIconViewModel
import com.mirego.trikot.viewmodels.mutable.MutableViewModel

class MutableInputTextWithIconListItemViewModel(override var comparableId: String = "") : InputTextWithIconListItemViewModel, MutableViewModel() {
    override val inputTextWithIcon = MutableInputTextWithIconViewModel()

    override fun isTheSame(other: ListItemViewModel): Boolean {
        return false
    }
}
