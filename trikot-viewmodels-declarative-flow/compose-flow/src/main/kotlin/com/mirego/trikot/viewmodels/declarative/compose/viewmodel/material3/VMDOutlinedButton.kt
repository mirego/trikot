package com.mirego.trikot.viewmodels.declarative.compose.viewmodel.material3

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.mirego.trikot.viewmodels.declarative.components.VMDButtonViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.hidden
import com.mirego.trikot.viewmodels.declarative.compose.extensions.isOverridingAlpha
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.content.VMDContent

@Composable
fun <C : VMDContent> VMDOutlinedButton(
    viewModel: VMDButtonViewModel<C>,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable (RowScope.(field: C) -> Unit)
) {
    val buttonViewModel: VMDButtonViewModel<C> by viewModel.observeAsState(excludedProperties = if (modifier.isOverridingAlpha()) listOf(viewModel::isHidden) else emptyList())

    OutlinedButton(
        onClick = buttonViewModel.actionBlock,
        modifier = Modifier
            .hidden(buttonViewModel.isHidden)
            .then(modifier),
        enabled = buttonViewModel.isEnabled,
        interactionSource = interactionSource
    ) {
        content(buttonViewModel.content)
    }
}
