package com.mirego.trikot.viewmodels.declarative.compose.viewmodel

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MenuDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.mirego.trikot.viewmodels.declarative.components.VMDPickerItemViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDPickerViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.isOverridingAlpha
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.extensions.vmdModifier

@Composable
fun <E : VMDPickerItemViewModel> VMDDropDownMenu(
    viewModel: VMDPickerViewModel<E>,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    properties: PopupProperties = PopupProperties(focusable = true),
    content: @Composable ColumnScope.(item: E, index: Int) -> Unit
) {
    val pickerViewModel by viewModel.observeAsState(excludedProperties = if (modifier.isOverridingAlpha()) listOf(viewModel::isHidden) else emptyList())

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier.vmdModifier(pickerViewModel),
        offset = offset,
        properties = properties
    ) {
        pickerViewModel.elements.forEachIndexed { index, item ->
            content(item, index)
        }
    }
}

@Composable
fun <E : VMDPickerItemViewModel> VMDDropDownMenuItem(
    pickerViewModel: VMDPickerViewModel<E>,
    viewModel: E,
    index: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    contentPadding: PaddingValues = MenuDefaults.DropdownMenuItemContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.(item: E, index: Int) -> Unit
) {
    val pickerItemViewModel by viewModel.observeAsState(excludedProperties = if (modifier.isOverridingAlpha()) listOf(viewModel::isHidden) else emptyList())

    DropdownMenuItem(
        onClick = {
            pickerViewModel.selectedIndex = index
            onClick()
        },
        modifier = modifier.vmdModifier(pickerItemViewModel),
        enabled = pickerItemViewModel.isEnabled,
        contentPadding = contentPadding,
        interactionSource = interactionSource
    ) {
        content(pickerItemViewModel, index)
    }
}
