package com.mirego.trikot.viewmodels.declarative.components

interface ToggleViewModel {
    val isOn: Boolean

    fun onValueChange(isOn: Boolean)
}
