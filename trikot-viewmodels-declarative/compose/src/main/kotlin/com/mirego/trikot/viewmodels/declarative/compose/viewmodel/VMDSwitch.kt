package com.mirego.trikot.viewmodels.declarative.compose.viewmodel

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Switch
import androidx.compose.material.SwitchColors
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.VMDToggleViewModel
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.compose.extensions.hidden
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.content.VMDContent
import com.mirego.trikot.viewmodels.declarative.content.VMDNoContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent

@Composable
fun VMDSwitch(
    modifier: Modifier = Modifier,
    componentModifier: Modifier = Modifier,
    viewModel: VMDToggleViewModel<VMDNoContent>,
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
    colors: SwitchColors = SwitchDefaults.colors()
) {
    val toggleViewModel: VMDToggleViewModel<C> by viewModel.observeAsState()

    VMDLabeledComponent(
        modifier = modifier
            .hidden(toggleViewModel.isHidden),
        label = { label(toggleViewModel.label) },
        content = {
            Switch(
                modifier = componentModifier,
                enabled = toggleViewModel.isEnabled,
                checked = toggleViewModel.isOn,
                colors = colors,
                onCheckedChange = { checked -> viewModel.onValueChange(checked) },
            )
        }
    )
}

@Preview
@Composable
fun EnabledSwitchPreview() {
    val toggleViewModel =
        VMDComponents.Toggle.withState(true, CancellableManager())
    VMDSwitch(viewModel = toggleViewModel)
}

@Preview
@Composable
fun DisabledSwitchPreview() {
    val toggleViewModel =
        VMDComponents.Toggle.withState(false, CancellableManager())
    VMDSwitch(viewModel = toggleViewModel)
}

@Preview
@Composable
fun SimpleTextSwitchPreview() {
    val toggleViewModel =
        VMDComponents.Toggle.withText("Label", true, CancellableManager())
    VMDSwitch(viewModel = toggleViewModel, label = { Text(it.text) })
}
