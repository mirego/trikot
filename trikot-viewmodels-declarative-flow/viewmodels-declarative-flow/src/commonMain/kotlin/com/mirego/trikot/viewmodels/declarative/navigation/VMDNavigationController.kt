package com.mirego.trikot.viewmodels.declarative.navigation

interface VMDNavigationController {
    fun push(
        navigationItem: VMDNavigationDestination<*>,
        options: VMDPushOptions? = null
    )

    fun pop(
        result: VMDNavigationResult?,
        options: VMDPopOptions? = null
    )
}
