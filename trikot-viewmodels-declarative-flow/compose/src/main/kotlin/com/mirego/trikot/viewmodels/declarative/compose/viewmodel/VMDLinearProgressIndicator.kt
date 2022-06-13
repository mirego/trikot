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
import com.mirego.trikot.viewmodels.declarative.components.VMDProgressViewModel
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.compose.extensions.hidden
import com.mirego.trikot.viewmodels.declarative.compose.extensions.isOverridingAlpha
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import kotlinx.coroutines.MainScope

@Composable
fun VMDLinearProgressIndicator(
    modifier: Modifier = Modifier,
    viewModel: VMDProgressViewModel,
    color: Color = MaterialTheme.colors.primary,
    backgroundColor: Color = color.copy(alpha = ProgressIndicatorDefaults.IndicatorBackgroundOpacity)
) {
    val progressViewModel: VMDProgressViewModel by viewModel.observeAsState(excludedProperties = if (modifier.isOverridingAlpha()) listOf(viewModel::isHidden) else emptyList())
    val animatedProgress by animateFloatAsState(targetValue = viewModel.determination?.progressRatio ?: 0f)
    if (viewModel.determination == null) {
        LinearProgressIndicator(
            modifier = modifier.hidden(progressViewModel.isHidden)
        )
    } else {
        LinearProgressIndicator(
            modifier = modifier.hidden(progressViewModel.isHidden),
            progress = animatedProgress,
            color = color,
            backgroundColor = backgroundColor
        )
    }
}

@Preview
@Composable
fun DeterminateLinearProgressIndicatorPreview() {
    val progressViewModel = VMDComponents.Progress.determinate(0.25f, MainScope())
    VMDLinearProgressIndicator(
        modifier = Modifier.fillMaxWidth(),
        viewModel = progressViewModel
    )
}
