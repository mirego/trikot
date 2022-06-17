package com.mirego.sample.ui.showcase.animation.types

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mirego.sample.ui.showcase.ComponentShowcaseTopBar
import com.mirego.sample.ui.theming.SampleTextStyle
import com.mirego.sample.ui.theming.medium
import com.mirego.sample.viewmodels.showcase.animation.types.AnimationTypeShowcaseViewModel
import com.mirego.sample.viewmodels.showcase.animation.types.AnimationTypesShowcaseViewModel
import com.mirego.sample.viewmodels.showcase.animation.types.AnimationTypesShowcaseViewModelPreview
import com.mirego.trikot.viewmodels.declarative.compose.extensions.animationSpec
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAnimatedPropertyAsState
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDButton
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDText

@Composable
private fun animateHorizontalAlignmentAsState(
    targetValue: Float,
    animationSpec: AnimationSpec<Float>,
    visibilityThreshold: Float = 0.01f,
    finishedListener: ((Float) -> Unit)? = null
): State<BiasAlignment.Horizontal> {
    val bias by animateFloatAsState(
        targetValue = targetValue,
        animationSpec = animationSpec,
        visibilityThreshold = visibilityThreshold,
        finishedListener = finishedListener
    )
    return derivedStateOf { BiasAlignment.Horizontal(bias) }
}

@Composable
fun AnimationTypesShowcaseView(animationTypesShowcaseViewModel: AnimationTypesShowcaseViewModel) {
    val viewModel: AnimationTypesShowcaseViewModel by animationTypesShowcaseViewModel.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = rememberScrollState())
    ) {
        ComponentShowcaseTopBar(viewModel)

        AnimationSection(viewModel.linear)
        AnimationSection(viewModel.easeIn)
        AnimationSection(viewModel.easeOut)
        AnimationSection(viewModel.easeInEaseOut)
        AnimationSection(viewModel.cubicBezier)
        AnimationSection(viewModel.spring)
    }
}

@Composable
private fun AnimationSection(animationTypeViewModel: AnimationTypeShowcaseViewModel) {
    val viewModel: AnimationTypeShowcaseViewModel by animationTypeViewModel.observeAsState()

    val linearAnimatedProperty by viewModel.observeAnimatedPropertyAsState(
        property = viewModel::isTrailing,
        transform = { isTrailing -> if (isTrailing) 1f else -1f }
    )
    /*val horizontalAlignment by animateHorizontalAlignmentAsState(
        targetValue = linearAnimatedProperty.value,
        animationSpec = linearAnimatedProperty.animationSpec()
    )*/

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, end = 16.dp, top = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            VMDText(
                viewModel = viewModel.title,
                style = SampleTextStyle.title2
            )

            VMDButton(
                viewModel = viewModel.animateButton
            ) { content ->
                Text(
                    modifier = Modifier
                        .background(MaterialTheme.colors.background, shape = RoundedCornerShape(6.dp))
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                    text = content.text,
                    style = SampleTextStyle.body.medium(),
                    color = MaterialTheme.colors.primary
                )
            }
        }

        Canvas(modifier = Modifier
            .size(30.dp)
            .padding(top = 10.dp), onDraw = {
            drawCircle(Color.Red)
        })
    }
}

@Preview(showSystemUi = true)
@Composable
fun AnimationTypesShowcaseViewPreview() {
    AnimationTypesShowcaseView(animationTypesShowcaseViewModel = AnimationTypesShowcaseViewModelPreview())
}
