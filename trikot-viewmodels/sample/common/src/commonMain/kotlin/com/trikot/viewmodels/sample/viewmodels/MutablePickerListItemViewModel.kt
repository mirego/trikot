package com.trikot.viewmodels.sample.viewmodels

import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.viewmodels.ListItemViewModel
import com.mirego.trikot.viewmodels.mutable.MutablePickerItemViewModel
import com.mirego.trikot.viewmodels.mutable.MutablePickerViewModel
import com.mirego.trikot.viewmodels.mutable.MutableViewModel

class MutablePickerListItemViewModel(override var comparableId: String = "") :
    PickerListItemViewModel, MutableViewModel() {

    override val picker = MutablePickerViewModel<String>().apply {
        elements = listOf(
            MutablePickerItemViewModel("Element 1", ""),
            MutablePickerItemViewModel("Element 2", ""),
            MutablePickerItemViewModel("Element 3", ""),
        ).just()
    }

    override fun isTheSame(other: ListItemViewModel): Boolean {
        return false
    }
}
