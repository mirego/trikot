package com.mirego.trikot.viewmodels.declarative.compose.viewmodel

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Switch
import androidx.compose.material.SwitchColors
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mirego.trikot.viewmodels.declarative.components.VMDToggleViewModel
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.compose.extensions.isOverridingAlpha
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.extensions.vmdModifier
import com.mirego.trikot.viewmodels.declarative.content.VMDContent
import com.mirego.trikot.viewmodels.declarative.content.VMDNoContent
import kotlinx.coroutines.MainScope

@Composable
fun VMDSwitch(
    modifier: Modifier = Modifier,
    componentModifier: Modifier = Modifier,
    viewModel: VMDToggleViewModel<VMDNoContent>,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: SwitchColors = SwitchDefaults.colors()
) {
    VMDSwitch(
        modifier = modifier,
        componentModifier = componentModifier,
        viewModel = viewModel,
        label = {},
        colors = colors
    )
}

@Composable
fun <C : VMDContent> VMDSwitch(
    modifier: Modifier = Modifier,
    componentModifier: Modifier = Modifier,
    viewModel: VMDToggleViewModel<C>,
    label: @Composable (RowScope.(field: C) -> Unit),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: SwitchColors = SwitchDefaults.colors()
) {
    val toggleViewModel: VMDToggleViewModel<C> by viewModel.observeAsState(excludedProperties = if (modifier.isOverridingAlpha()) listOf(viewModel::isHidden) else emptyList())

    VMDLabeledComponent(
        modifier = modifier.vmdModifier(toggleViewModel),
        label = { label(toggleViewModel.label) },
        content = {
            Switch(
                modifier = componentModifier,
                enabled = toggleViewModel.isEnabled,
                checked = toggleViewModel.isOn,
                colors = colors,
                interactionSource = interactionSource,
                onCheckedChange = { checked -> viewModel.onValueChange(checked) }
            )
        }
    )
}

@Preview
@Composable
fun EnabledSwitchPreview() {
    val toggleViewModel =
        VMDComponents.Toggle.withState(true, MainScope())
    VMDSwitch(viewModel = toggleViewModel)
}

@Preview
@Composable
fun DisabledSwitchPreview() {
    val toggleViewModel =
        VMDComponents.Toggle.withState(false, MainScope())
    VMDSwitch(viewModel = toggleViewModel)
}

@Preview
@Composable
fun SimpleTextSwitchPreview() {
    val toggleViewModel =
        VMDComponents.Toggle.withText("Label", true, MainScope())
    VMDSwitch(viewModel = toggleViewModel, label = { Text(it.text) })
}
