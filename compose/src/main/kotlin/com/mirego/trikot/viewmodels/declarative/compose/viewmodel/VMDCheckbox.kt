package com.mirego.trikot.viewmodels.declarative.compose.viewmodel

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Text
import androidx.compose.ui.tooling.preview.Preview
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponentsFactory
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mirego.trikot.viewmodels.declarative.components.VMDToggleViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.hidden
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.content.VMDContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent

@Composable
fun VMDCheckbox(
    modifier: Modifier = Modifier,
    componentModifier: Modifier = Modifier,
    viewModel: VMDToggleViewModel<VMDTextContent>,
    colors: CheckboxColors = CheckboxDefaults.colors()
) {
    VMDCheckbox(
        modifier = modifier,
        componentModifier = componentModifier,
        viewModel = viewModel,
        label = { Text(text = it.text) },
        colors = colors
    )
}

@Composable
fun <C : VMDContent> VMDCheckbox(
    modifier: Modifier = Modifier,
    componentModifier: Modifier = Modifier,
    viewModel: VMDToggleViewModel<C>,
    label: @Composable (RowScope.(field: C) -> Unit),
    colors: CheckboxColors = CheckboxDefaults.colors()
) {
    val toggleViewModel: VMDToggleViewModel<C> by viewModel.observeAsState()

    VMDLabeledComponent(
        modifier = modifier.hidden(viewModel.isHidden),
        label = { label(toggleViewModel.content) },
        content = {
            Checkbox(
                modifier = componentModifier,
                enabled = toggleViewModel.enabled,
                checked = toggleViewModel.isOn,
                colors = colors,
                onCheckedChange = { checked -> viewModel.onValueChange(checked) },
            )
        }
    )
}

@Preview
@Composable
fun EnabledToggleCheckboxPreview() {
    val toggleViewModel = VMDComponentsFactory.Companion.Toggle.withState(true, CancellableManager())
    VMDCheckbox(viewModel = toggleViewModel, label = {})
}

@Preview
@Composable
fun DisabledToggleCheckboxPreview() {
    val toggleViewModel = VMDComponentsFactory.Companion.Toggle.withState(false, CancellableManager())
    VMDCheckbox(viewModel = toggleViewModel, label = {})
}

@Preview
@Composable
fun SimpleTextToggleCheckboxPreview() {
    val toggleViewModel = VMDComponentsFactory.Companion.Toggle.withText("Label", true, CancellableManager())
    VMDCheckbox(viewModel = toggleViewModel)
}