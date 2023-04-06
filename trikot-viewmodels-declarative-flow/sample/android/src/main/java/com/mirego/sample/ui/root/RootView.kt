package com.mirego.sample.ui.root

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.mirego.sample.viewmodels.root.AppEntryPoint
import com.mirego.trikot.viewmodels.declarative.navigation.VMDPlatformNavigationControllerImpl

@Composable
fun RootView() {
    val navController = rememberNavController()
    val appEntryPoint = AppEntryPoint(VMDPlatformNavigationControllerImpl(navController))

    SampleNavigationView(
        navHostController = navController,
        appEntryPoint = appEntryPoint
    )
}
