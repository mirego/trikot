package com.trikot.viewmodels.sample.viewmodels

import com.mirego.trikot.viewmodels.mutable.MutableLabelViewModel
import com.mirego.trikot.viewmodels.mutable.MutableViewModel

class MutableNavigableListItemViewModel(override var comparableId: String = "") : NavigableListItemViewModel, MutableViewModel() {
    override val title = MutableLabelViewModel()
}
