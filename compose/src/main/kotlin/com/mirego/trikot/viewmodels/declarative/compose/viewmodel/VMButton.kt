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
import com.mirego.trikot.viewmodels.declarative.components.ButtonViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.ui.hidden
import com.mirego.trikot.viewmodels.declarative.properties.Content

@Composable
fun <C : Content> VMButton(
    modifier: Modifier = Modifier,
    viewModel: ButtonViewModel<C>,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable (BoxScope.(field: C) -> Unit)
) {
    val buttonViewModel: ButtonViewModel<C> by viewModel.observeAsState()

    Box(
        modifier = modifier
            .clickable(
                enabled = buttonViewModel.enabled,
                onClick = viewModel.action,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            )
            .hidden(buttonViewModel.hidden),
        contentAlignment = contentAlignment,
        content = { content(buttonViewModel.content) }
    )
}
