package com.mirego.trikot.viewmodels.declarative.compose.viewmodel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mirego.trikot.viewmodels.declarative.components.VMDButtonViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.hidden
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.content.VMDContent

@Composable
fun <C : VMDContent> VMDButton(
    modifier: Modifier = Modifier,
    viewModel: VMDButtonViewModel<C>,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable (BoxScope.(field: C) -> Unit)
) {
    val buttonViewModel: VMDButtonViewModel<C> by viewModel.observeAsState()

    Box(
        modifier = modifier
            .hidden(buttonViewModel.isHidden)
            .clickable(
                enabled = buttonViewModel.enabled,
                onClick = viewModel.action,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            ),
        contentAlignment = contentAlignment,
        content = { content(buttonViewModel.content) }
    )
}
