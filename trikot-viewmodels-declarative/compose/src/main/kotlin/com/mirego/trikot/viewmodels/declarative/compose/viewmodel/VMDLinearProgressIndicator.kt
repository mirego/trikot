package com.mirego.trikot.viewmodels.declarative.compose.viewmodel

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.VMDProgressViewModel
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.compose.extensions.hidden
import com.mirego.trikot.viewmodels.declarative.compose.extensions.isOverridingAlpha
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState

@Composable
fun VMDLinearProgressIndicator(
    modifier: Modifier = Modifier,
    viewModel: VMDProgressViewModel,
    color: Color = MaterialTheme.colors.primary,
    backgroundColor: Color = color.copy(alpha = ProgressIndicatorDefaults.IndicatorBackgroundOpacity)
) {
    val progressViewModel: VMDProgressViewModel by viewModel.observeAsState(excludedProperties = if (modifier.isOverridingAlpha()) listOf(viewModel::isHidden) else emptyList())
    val animatedProgress by animateFloatAsState(targetValue = viewModel.determination?.progressRatio ?: 0f)

    val newModifier = Modifier
        .hidden(progressViewModel.isHidden)
        .then(modifier)

    if (viewModel.determination == null) {
        LinearProgressIndicator(
            modifier = newModifier,
            color = color,
            backgroundColor = backgroundColor
        )
    } else {
        LinearProgressIndicator(
            modifier = newModifier,
            progress = animatedProgress,
            color = color,
            backgroundColor = backgroundColor
        )
    }
}

@Preview
@Composable
private fun DeterminateLinearProgressIndicatorPreview() {
    val progressViewModel = VMDComponents.Progress.determinate(0.25f, CancellableManager())
    VMDLinearProgressIndicator(
        modifier = Modifier.fillMaxWidth(),
        viewModel = progressViewModel
    )
}
