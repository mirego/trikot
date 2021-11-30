package com.mirego.sample.ui.showcase.progress

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mirego.sample.ui.theming.sampleTypography
import com.mirego.sample.viewmodels.showcase.progress.ProgressShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.LocalImage
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDButton
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDCircularProgressIndicator
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDLinearProgressIndicator
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDText

@Composable
fun ProgressShowcaseView(progressShowcaseViewModel: ProgressShowcaseViewModel) {
    val viewModel: ProgressShowcaseViewModel by progressShowcaseViewModel.observeAsState()

    Column(modifier = Modifier.fillMaxWidth()) {

        TopAppBar(
            title = { VMDText(viewModel = viewModel.title) },
            actions = {
                VMDButton(viewModel = viewModel.closeButton) {
                    LocalImage(
                        imageResource = viewModel.closeButton.content.image
                    )
                }
            }
        )

        VMDText(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            viewModel = viewModel.linearDeterminateProgressTitle,
            style = sampleTypography.title2
        )

        VMDLinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            progressViewModel = viewModel.determinateProgress
        )

        VMDText(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            viewModel = viewModel.linearIndeterminateProgressTitle,
            style = sampleTypography.title2
        )

        VMDLinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            progressViewModel = viewModel.indeterminateProgress
        )

        VMDText(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            viewModel = viewModel.circularDeterminateProgressTitle,
            style = sampleTypography.title2
        )

        VMDCircularProgressIndicator(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            progressViewModel = viewModel.determinateProgress
        )

        VMDText(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            viewModel = viewModel.circularIndeterminateProgressTitle,
            style = sampleTypography.title2
        )

        VMDCircularProgressIndicator(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
            progressViewModel = viewModel.indeterminateProgress
        )
    }
}
