package com.mirego.trikot.viewmodels.declarative.navigation

import androidx.navigation.NavHostController

class VMDPlatformNavigationControllerImpl(private val navController: NavHostController) : VMDPlatformNavigationController {

    override fun push(destination: VMDNavigationDestination<*>, options: VMDPushOptions?) {
        val input = destination.input?.serialized.orEmpty()
        navController.navigate(route = "${destination.identifier}/$input")
    }

    override fun pop(destination: VMDNavigationDestination<*>, options: VMDPopOptions?) {
        navController.popBackStack()
    }
}
