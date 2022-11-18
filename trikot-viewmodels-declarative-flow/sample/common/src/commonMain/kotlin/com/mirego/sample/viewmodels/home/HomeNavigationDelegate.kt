package com.mirego.sample.viewmodels.home

import com.mirego.trikot.viewmodels.declarative.controller.VMDNavigationDelegate

interface HomeNavigationDelegate : VMDNavigationDelegate {
    fun navigateToTextShowcase()

    fun navigateToButtonShowcase()

    fun navigateToImageShowcase()

    fun navigateToTextFieldShowcase()

    fun navigateToToggleShowcase()

    fun navigateToProgressShowcase()

    fun navigateToAnimationTypesShowcase()

    fun navigateToListShowcase()

    fun navigateToPickerShowcase()

    fun navigateToSnackbarShowcase()
}
