package com.mirego.sample.viewmodels.base

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect abstract class ViewModelController() {
    protected open fun onCleared()
}
