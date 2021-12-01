package com.mirego.sample.ui.showcase.image

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mirego.sample.ui.theming.sampleTypography
import com.mirego.sample.viewmodels.showcase.image.ImageShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDButton
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDImage
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDText

@Composable
fun ImageShowcaseView(imageShowcaseViewModel: ImageShowcaseViewModel) {
    val viewModel: ImageShowcaseViewModel by imageShowcaseViewModel.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = rememberScrollState())
    ) {

        TopAppBar(
            title = { VMDText(viewModel = viewModel.title) },
            actions = {
                VMDButton(viewModel = viewModel.closeButton) {
                    VMDImage(
                        imageDescriptor = viewModel.closeButton.content.image
                    )
                }
            }
        )

        VMDText(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            viewModel = viewModel.localImageTitle,
            style = sampleTypography.title2
        )

        VMDImage(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            viewModel = viewModel.localImage
        )

        VMDText(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            viewModel = viewModel.remoteImageTitle,
            style = sampleTypography.title2
        )

        VMDImage(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            viewModel = viewModel.remoteImage
        )

        VMDText(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            viewModel = viewModel.localImageDescriptorTitle,
            style = sampleTypography.title2
        )

        VMDImage(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            imageDescriptor = viewModel.localImageDescriptor
        )

        VMDText(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            viewModel = viewModel.remoteImageDescriptorTitle,
            style = sampleTypography.title2
        )

        VMDImage(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
            imageDescriptor = viewModel.remoteImageDescriptor
        )
    }
}
