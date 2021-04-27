package com.mirego.trikot.viewmodels.declarative.compose.viewmodel

import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mirego.trikot.viewmodels.declarative.components.ToggleViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.ui.hidden
import com.mirego.trikot.viewmodels.declarative.properties.Content

@Composable
fun <C : Content> VMToggleCheckbox(
    modifier: Modifier = Modifier,
    viewModel: ToggleViewModel<C>,
    colors: CheckboxColors = CheckboxDefaults.colors()
) {
    val toggleViewModel: ToggleViewModel<C> by viewModel.observeAsState()
    Checkbox(
        modifier = Modifier
            .hidden(toggleViewModel.hidden)
            .then(modifier),
        enabled = toggleViewModel.enabled,
        checked = toggleViewModel.isOn,
        colors = colors,
        onCheckedChange = { checked -> viewModel.onValueChange(checked) },
    )
}
