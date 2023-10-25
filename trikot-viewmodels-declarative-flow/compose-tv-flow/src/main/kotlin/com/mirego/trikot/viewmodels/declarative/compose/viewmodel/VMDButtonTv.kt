package com.mirego.trikot.viewmodels.declarative.compose.viewmodel

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonBorder
import androidx.tv.material3.ButtonColors
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.ButtonGlow
import androidx.tv.material3.ButtonScale
import androidx.tv.material3.ButtonShape
import androidx.tv.material3.ExperimentalTvMaterial3Api
import com.mirego.trikot.viewmodels.declarative.components.VMDButtonViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.isOverridingAlpha
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.extensions.vmdModifier
import com.mirego.trikot.viewmodels.declarative.content.VMDContent

@ExperimentalTvMaterial3Api
@Composable
fun <C : VMDContent> VMDButtonTv(
    modifier: Modifier = Modifier,
    viewModel: VMDButtonViewModel<C>,
    glow: ButtonGlow = ButtonDefaults.glow(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    border: ButtonBorder = ButtonDefaults.border(),
    tonalElevation: Dp = 0.0.dp,
    colors: ButtonColors = ButtonDefaults.colors(),
    shape: ButtonShape = ButtonDefaults.shape(),
    scale: ButtonScale = ButtonDefaults.scale(),
    content: @Composable (RowScope.(field: C) -> Unit)
) {
    val buttonViewModel: VMDButtonViewModel<C> by viewModel.observeAsState(excludedProperties = if (modifier.isOverridingAlpha()) listOf(viewModel::isHidden) else emptyList())

    Button(
        modifier = modifier
            .vmdModifier(buttonViewModel),
        onClick = buttonViewModel.actionBlock,
        enabled = buttonViewModel.isEnabled,
        glow = glow,
        interactionSource = interactionSource,
        contentPadding = contentPadding,
        border = border,
        tonalElevation = tonalElevation,
        colors = colors,
        shape = shape,
        scale = scale,
        content = { content(buttonViewModel.content) }
    )
}
