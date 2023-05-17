package com.mirego.trikot.viewmodels.declarative.compose.viewmodel

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.VMDProgressViewModel
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.compose.extensions.hidden
import com.mirego.trikot.viewmodels.declarative.compose.extensions.isOverridingAlpha
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState

@Composable
fun VMDCircularProgressIndicator(
    modifier: Modifier = Modifier,
    viewModel: VMDProgressViewModel,
    color: Color = MaterialTheme.colors.primary,
    strokeWidth: Dp = ProgressIndicatorDefaults.StrokeWidth,
    backgroundColor: Color = Color.Transparent,
    strokeCap: StrokeCap = StrokeCap.Square
) {
    val progressViewModel: VMDProgressViewModel by viewModel.observeAsState(excludedProperties = if (modifier.isOverridingAlpha()) listOf(viewModel::isHidden) else emptyList())
    val animatedProgress by animateFloatAsState(targetValue = progressViewModel.determination?.progressRatio ?: 0f)

    val newModifier = Modifier
        .hidden(progressViewModel.isHidden)
        .then(modifier)

    if (viewModel.determination == null) {
        CircularProgressIndicator(
            modifier = newModifier,
            color = color,
            strokeWidth = strokeWidth,
            backgroundColor = backgroundColor,
            strokeCap = strokeCap
        )
    } else {
        CircularProgressIndicator(
            modifier = newModifier,
            progress = animatedProgress,
            color = color,
            strokeWidth = strokeWidth,
            backgroundColor = backgroundColor,
            strokeCap = strokeCap
        )
    }
}

@Preview
@Composable
fun DeterminateCircularProgressIndicatorPreview() {
    val progressViewModel = VMDComponents.Progress.determinate(0.25f, CancellableManager())
    VMDCircularProgressIndicator(
        viewModel = progressViewModel
    )
}
