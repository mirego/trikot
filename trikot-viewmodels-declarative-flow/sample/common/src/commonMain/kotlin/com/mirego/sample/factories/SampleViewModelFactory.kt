package com.mirego.sample.factories

import com.mirego.sample.navigation.DialogInput
import com.mirego.sample.navigation.GeneratedEnumOfDestinations
import com.mirego.sample.navigation.TextShowcaseInput
import com.mirego.sample.viewmodels.dialog.DialogViewModelImpl
import com.mirego.sample.viewmodels.home.HomeViewModelImplV2
import com.mirego.sample.viewmodels.showcase.components.text.TextShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.navigation.VMDNavigationController
import com.mirego.trikot.viewmodels.declarative.util.CoroutineScopeProvider
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.serialization.json.Json

class SampleViewModelFactory(
    private val i18N: I18N,
    private val navigationController: VMDNavigationController
) {
    fun <T : VMDViewModel> create(navigationDestination: GeneratedEnumOfDestinations, rawInput: String): T {
        return when (navigationDestination) {
            GeneratedEnumOfDestinations.Home -> HomeViewModelImplV2(
                viewModelScope = createCoroutineScope(),
                i18N = i18N,
                navigationController = navigationController
            ) as T

            GeneratedEnumOfDestinations.TextShowcase -> TextShowcaseViewModelImpl(
                i18N = i18N,
                coroutineScope = createCoroutineScope(),
                title = Json.decodeFromString(TextShowcaseInput.serializer(), rawInput).textInput
            ) as T

            GeneratedEnumOfDestinations.DialogShowcase -> DialogViewModelImpl(
                input = Json.decodeFromString(DialogInput.serializer(), rawInput),
                navigationController = navigationController,
                coroutineScope = createCoroutineScope()
            ) as T
        }
    }

    private fun createCoroutineScope() = CoroutineScopeProvider.provideMainWithSuperviserJob(
        CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
        }
    )
}
