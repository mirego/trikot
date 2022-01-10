package com.mirego.trikot.viewmodels

interface PickerItemViewModel<T> {
    var comparableId: String
    var displayName: String
    var value: T
}
