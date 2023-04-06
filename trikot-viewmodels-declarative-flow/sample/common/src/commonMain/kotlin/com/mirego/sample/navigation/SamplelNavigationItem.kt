package com.mirego.sample.navigation

import com.mirego.trikot.viewmodels.declarative.navigation.VMDNavigationDestination
import com.mirego.trikot.viewmodels.declarative.navigation.VMDNavigationDestinationType
import com.mirego.trikot.viewmodels.declarative.navigation.VMDNavigationInput
import com.mirego.trikot.viewmodels.declarative.navigation.VMDNavigationResult
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.Json

enum class GeneratedEnumOfDestinations(val type: VMDNavigationDestinationType) {
    Home(VMDNavigationDestinationType.ROOT),
    TextShowcase(VMDNavigationDestinationType.MODAL),
    DialogShowcase(VMDNavigationDestinationType.DIALOG);

    val identifier = name
}

sealed class NavigationDestination<T : VMDNavigationResult> : VMDNavigationDestination<T>() {

    class Home : NavigationDestination<VMDNavigationResult>() {
        override val type = VMDNavigationDestinationType.ROOT
        override val input = null
        override val resultCallback = null
    }

    data class TextShowcase(
        override val input: TextShowcaseInput
    ) : NavigationDestination<VMDNavigationResult>() {
        override val type = VMDNavigationDestinationType.MODAL
        override val resultCallback = null
    }

    data class DialogShowcase(
        override val input: DialogInput,
        override val resultCallback: (DialogResult) -> Unit
    ) : NavigationDestination<DialogResult>() {
        override val type = VMDNavigationDestinationType.DIALOG
    }
}

@Serializable
data class TextShowcaseInput(
    val textInput: String
) : VMDNavigationInput {
    @Transient
    override val serialized = Json.encodeToString(serializer(), this)
}

@Serializable
data class DialogInput(
    val message: String,
    val choice1: String,
    val choice2: String
) : VMDNavigationInput {
    @Transient
    override val serialized = Json.encodeToString(serializer(), this)
}

@Serializable
data class DialogResult(
    val choice: String
) : VMDNavigationResult
