package com.mirego.trikot.viewmodels.declarative.compose.viewmodel

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

@Composable
fun <C : VMDContent> VMDToggleCheckbox(
    modifier: Modifier = Modifier,
    viewModel: VMDToggleViewModel<C>,
    colors: CheckboxColors = CheckboxDefaults.colors()
) {
    val toggleViewModel: VMDToggleViewModel<C> by viewModel.observeAsState()
    Checkbox(
        modifier = Modifier
            .hidden(toggleViewModel.isHidden)
            .then(modifier),
        enabled = toggleViewModel.enabled,
        checked = toggleViewModel.isOn,
        colors = colors,
        onCheckedChange = { checked -> viewModel.onValueChange(checked) },
    )
}
