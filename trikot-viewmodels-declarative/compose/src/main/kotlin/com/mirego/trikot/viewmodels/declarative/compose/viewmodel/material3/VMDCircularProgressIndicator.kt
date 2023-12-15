package com.mirego.trikot.viewmodels.declarative.compose.viewmodel.material3

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
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
    viewModel: VMDProgressViewModel,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = ProgressIndicatorDefaults.CircularStrokeWidth,
    trackColor: Color = ProgressIndicatorDefaults.circularTrackColor,
    strokeCap: StrokeCap = StrokeCap.Square
) {
    val progressViewModel: VMDProgressViewModel by viewModel.observeAsState(excludedProperties = if (modifier.isOverridingAlpha()) listOf(viewModel::isHidden) else emptyList())
    val animatedProgress by animateFloatAsState(targetValue = progressViewModel.determination?.progressRatio ?: 0f, label = "animatedProgress")

    val newModifier = Modifier
        .hidden(progressViewModel.isHidden)
        .then(modifier)

    if (viewModel.determination == null) {
        CircularProgressIndicator(
            modifier = newModifier,
            color = color,
            strokeWidth = strokeWidth,
            trackColor = trackColor,
            strokeCap = strokeCap
        )
    } else {
        CircularProgressIndicator(
            modifier = newModifier,
            progress = animatedProgress,
            color = color,
            strokeWidth = strokeWidth,
            trackColor = trackColor,
            strokeCap = strokeCap
        )
    }
}

@Preview
@Composable
private fun DeterminateCircularProgressIndicatorPreview() {
    val progressViewModel = VMDComponents.Progress.determinate(0.25f, CancellableManager())
    VMDCircularProgressIndicator(
        viewModel = progressViewModel
    )
}
