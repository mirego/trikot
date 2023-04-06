package com.mirego.sample.ui.root

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.mirego.sample.factories.SampleViewModelFactory
import com.mirego.sample.navigation.GeneratedEnumOfDestinations
import com.mirego.sample.ui.dialog.DialogView
import com.mirego.sample.ui.home.HomeView
import com.mirego.sample.ui.showcase.components.text.TextShowcaseView
import com.mirego.sample.viewmodels.root.AppEntryPoint
import com.mirego.trikot.viewmodels.declarative.navigation.VMDNavigationDestinationType

@Composable
fun SampleNavigationView(
    navHostController: NavHostController,
    appEntryPoint: AppEntryPoint
) {
    val viewModelFactory = appEntryPoint.viewModelFactory
    NavHost(
        navController = navHostController,
        startDestination = appEntryPoint.initialDestination.identifier,
    ) {
        GeneratedEnumOfDestinations.values().forEach { navigationDestination ->
            vmdComposable2(navigationDestination) { identifier: GeneratedEnumOfDestinations, rawInput ->
                when (identifier) {
                    GeneratedEnumOfDestinations.Home -> HomeView(viewModelFactory.create(identifier, rawInput))
                    GeneratedEnumOfDestinations.TextShowcase -> TextShowcaseView(viewModelFactory.create(identifier, rawInput))
                    GeneratedEnumOfDestinations.DialogShowcase -> DialogView(viewModelFactory.create(identifier, rawInput))
                }
            }
        }
    }
}

fun NavGraphBuilder.vmdComposable2(
    identifier: GeneratedEnumOfDestinations,
    content: @Composable (GeneratedEnumOfDestinations, String) -> Unit
) {
    when (identifier.type) {
        VMDNavigationDestinationType.ROOT -> composable(
            route = identifier.identifier
        ) {
            content(identifier, "")
        }

        VMDNavigationDestinationType.MODAL -> composable(
            route = "${identifier.identifier}/{input}"
        ) { backStackEntry ->
            val input = backStackEntry.arguments?.getString("input").orEmpty()
            content(identifier, input)
        }

        VMDNavigationDestinationType.DIALOG -> dialog(
            route = "${identifier.identifier}/{input}"
        ) { backStackEntry ->
            val input = backStackEntry.arguments?.getString("input").orEmpty()
            content(identifier, input)
        }
    }
}
