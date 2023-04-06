package com.mirego.trikot.viewmodels.declarative.navigation

interface VMDPlatformNavigationController {

    fun push(
        destination: VMDNavigationDestination<*>,
        options: VMDPushOptions?
    )

    fun pop(
        destination: VMDNavigationDestination<*>,
        options: VMDPopOptions?
    )
}
