package com.trikot.viewmodels.sample.viewmodels

import com.mirego.trikot.viewmodels.mutable.MutableLabelViewModel
import com.mirego.trikot.viewmodels.mutable.MutableViewModel

class MutableLabelListItemViewModel(override var comparableId: String = "") : LabelListItemViewModel, MutableViewModel() {
    override val label = MutableLabelViewModel()
}
