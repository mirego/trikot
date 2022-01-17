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
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState

@Composable
fun VMDLinearProgressIndicator(
    modifier: Modifier = Modifier,
    progressViewModel: VMDProgressViewModel,
    color: Color = MaterialTheme.colors.primary,
    backgroundColor: Color = color.copy(alpha = ProgressIndicatorDefaults.IndicatorBackgroundOpacity)
) {
    val viewModel: VMDProgressViewModel by progressViewModel.observeAsState()
    val animatedProgress by animateFloatAsState(targetValue = viewModel.determination?.progressRatio ?: 0f)
    if (viewModel.determination == null) {
        LinearProgressIndicator(modifier = modifier.hidden(viewModel.isHidden))
    } else {
        LinearProgressIndicator(
            modifier = modifier.hidden(viewModel.isHidden),
            progress = animatedProgress,
            color = color,
            backgroundColor = backgroundColor
        )
    }
}

@Preview
@Composable
fun DeterminateLinearProgressIndicatorPreview() {
    val progressViewModel = VMDComponents.Progress.determinate(0.25f, CancellableManager())
    VMDLinearProgressIndicator(
        modifier = Modifier.fillMaxWidth(),
        progressViewModel = progressViewModel
    )
}
