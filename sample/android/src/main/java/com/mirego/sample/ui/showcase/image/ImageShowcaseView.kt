package com.mirego.sample.ui.showcase.image

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mirego.sample.ui.showcase.ComponentShowcaseTitle
import com.mirego.sample.ui.showcase.ComponentShowcaseTopBar
import com.mirego.sample.viewmodels.showcase.image.ImageShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDImage

@Composable
fun ImageShowcaseView(imageShowcaseViewModel: ImageShowcaseViewModel) {
    val viewModel: ImageShowcaseViewModel by imageShowcaseViewModel.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = rememberScrollState())
    ) {

        ComponentShowcaseTopBar(viewModel)

        ComponentShowcaseTitle(viewModel.localImageTitle)

        VMDImage(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            viewModel = viewModel.localImage
        )

        ComponentShowcaseTitle(viewModel.remoteImageTitle)

        VMDImage(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            viewModel = viewModel.remoteImage
        )

        ComponentShowcaseTitle(viewModel.localImageDescriptorTitle)

        VMDImage(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            imageDescriptor = viewModel.localImageDescriptor
        )

        ComponentShowcaseTitle(viewModel.remoteImageDescriptorTitle)

        VMDImage(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
            imageDescriptor = viewModel.remoteImageDescriptor
        )
    }
}
