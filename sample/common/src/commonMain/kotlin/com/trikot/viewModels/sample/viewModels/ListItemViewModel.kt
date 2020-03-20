package com.trikot.viewmodels.sample.viewmodels

import com.mirego.trikot.viewmodels.ViewModel

interface ListItemViewModel : ViewModel {
    var comparableId: String
    fun isTheSame(other: ListItemViewModel): Boolean {
        return comparableId == other.comparableId
    }
    fun haveTheSameContent(other: ListItemViewModel): Boolean {
        return true
    }
}
