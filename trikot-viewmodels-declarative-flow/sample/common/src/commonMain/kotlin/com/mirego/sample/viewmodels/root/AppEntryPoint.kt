package com.mirego.sample.viewmodels.root

import com.mirego.sample.factories.SampleViewModelFactory
import com.mirego.sample.navigation.GeneratedEnumOfDestinations
import com.mirego.sample.navigation.NavigationDestination
import com.mirego.sample.viewmodels.home.HomeViewModel
import com.mirego.trikot.kword.KWord
import com.mirego.trikot.viewmodels.declarative.navigation.VMDNavigationControllerImpl
import com.mirego.trikot.viewmodels.declarative.navigation.VMDNavigationDestination
import com.mirego.trikot.viewmodels.declarative.navigation.VMDPlatformNavigationController

class AppEntryPoint(platformNavigationController: VMDPlatformNavigationController) {

    val initialDestination: VMDNavigationDestination<*> = NavigationDestination.Home()

    val viewModelFactory = SampleViewModelFactory(
        i18N = KWord,
        navigationController = VMDNavigationControllerImpl(platformNavigationController, initialDestination)
    )
}
