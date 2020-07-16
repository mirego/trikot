package com.mirego.trikot.viewmodels.mutable

import com.mirego.trikot.viewmodels.ListItemViewModel

open class MutableListItemViewModel : MutableViewModel(), ListItemViewModel {
    override var comparableId = this.hashCode().toString()
}
