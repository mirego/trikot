package com.trikot.viewmodels.sample.viewmodels

import com.mirego.trikot.viewmodels.mutable.MutableViewModel

class MutableViewListItemViewModel(override var comparableId: String = "") : ViewListItemViewModel, MutableViewModel() {
    override val view = MutableViewModel()
}
