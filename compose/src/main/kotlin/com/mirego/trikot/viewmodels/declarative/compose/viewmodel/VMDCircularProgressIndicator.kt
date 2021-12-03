package com.mirego.trikot.viewmodels.declarative.compose.viewmodel

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.VMDProgressViewModel
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.compose.extensions.hidden
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState

@Composable
fun VMDCircularProgressIndicator(
    modifier: Modifier = Modifier,
    progressViewModel: VMDProgressViewModel,
    color: Color = MaterialTheme.colors.primary,
    strokeWidth: Dp = ProgressIndicatorDefaults.StrokeWidth
) {
    val viewModel: VMDProgressViewModel by progressViewModel.observeAsState()
    val animatedProgress by animateFloatAsState(targetValue = viewModel.determination?.progressRatio ?: 0f)
    if (viewModel.determination == null) {
        CircularProgressIndicator(modifier = modifier.hidden(viewModel.isHidden))
    } else {
        CircularProgressIndicator(
            modifier = modifier.hidden(viewModel.isHidden),
            progress = animatedProgress,
            color = color,
            strokeWidth = strokeWidth
        )
    }
}

@Preview
@Composable
fun DeterminateCircularProgressIndicatorPreview() {
    val progressViewModel = VMDComponents.Progress.determinate(0.25f, CancellableManager())
    VMDCircularProgressIndicator(
        progressViewModel = progressViewModel
    )
}