package com.mirego.trikot.viewmodels.declarative.compose.viewmodel

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import com.mirego.trikot.viewmodels.declarative.components.VMDButtonViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.isOverridingAlpha
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.extensions.vmdModifier
import com.mirego.trikot.viewmodels.declarative.content.VMDContent

@Composable
fun <C : VMDContent> VMDButton(
    modifier: Modifier = Modifier,
    viewModel: VMDButtonViewModel<C>,
    contentAlignment: Alignment = Alignment.Center,
    propagateMinConstraints: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    indication: Indication? = ripple(),
    role: Role? = Role.Button,
    content: @Composable (BoxScope.(field: C) -> Unit)
) {
    val buttonViewModel: VMDButtonViewModel<C> by viewModel.observeAsState(excludedProperties = if (modifier.isOverridingAlpha()) listOf(viewModel::isHidden) else emptyList())

    Box(
        modifier = modifier
            .vmdModifier(buttonViewModel)
            .clickable(
                enabled = buttonViewModel.isEnabled,
                onClick = viewModel.actionBlock,
                interactionSource = interactionSource,
                indication = indication,
                role = role
            ),
        contentAlignment = contentAlignment,
        propagateMinConstraints = propagateMinConstraints,
        content = { content(buttonViewModel.content) }
    )
}
