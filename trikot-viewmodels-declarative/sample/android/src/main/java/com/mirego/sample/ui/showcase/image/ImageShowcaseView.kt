package com.mirego.sample.ui.showcase.image

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.ImagePainter
import com.mirego.sample.resource.SampleImageProvider
import com.mirego.sample.ui.showcase.ComponentShowcaseTitle
import com.mirego.sample.ui.showcase.ComponentShowcaseTopBar
import com.mirego.sample.ui.theming.SampleTextStyle
import com.mirego.sample.viewmodels.showcase.image.ImageShowcaseViewModel
import com.mirego.sample.viewmodels.showcase.image.ImageShowcaseViewModelPreview
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.LocalImage
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDImage
import com.mirego.trikot.viewmodels.declarative.configuration.TrikotViewModelDeclarative

@Composable
fun ImageShowcaseView(imageShowcaseViewModel: ImageShowcaseViewModel) {
    val viewModel: ImageShowcaseViewModel by imageShowcaseViewModel.observeAsState()
    val imageAspectRatio = 1.5f

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
                .aspectRatio(imageAspectRatio)
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            viewModel = viewModel.localImage,
            contentScale = ContentScale.Crop
        )

        ComponentShowcaseTitle(viewModel.remoteImageTitle)

        VMDImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(imageAspectRatio)
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            viewModel = viewModel.remoteImage,
            contentScale = ContentScale.Crop
        )

        ComponentShowcaseTitle(viewModel.localImageDescriptorTitle)

        VMDImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(imageAspectRatio)
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            imageDescriptor = viewModel.localImageDescriptor,
            contentScale = ContentScale.Crop
        )

        ComponentShowcaseTitle(viewModel.remoteImageDescriptorTitle)

        VMDImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(imageAspectRatio)
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
            imageDescriptor = viewModel.remoteImageDescriptor,
            contentScale = ContentScale.Crop
        )

        ComponentShowcaseTitle(viewModel.placeholderImageTitle)

        VMDImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(imageAspectRatio)
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
            viewModel = viewModel.placeholderImage,
            contentScale = ContentScale.Crop,
            placeholderContentScale = ContentScale.Crop
        )

        ComponentShowcaseTitle(viewModel.complexPlaceholderImageTitle)

        val imageModifier = Modifier
            .fillMaxWidth()
            .aspectRatio(imageAspectRatio)
            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)

        VMDImage(
            modifier = imageModifier,
            viewModel = viewModel.complexPlaceholderImage,
            contentScale = ContentScale.Crop,
            placeholderContentScale = ContentScale.Crop,
            placeholder = { placeholderImageResource, state ->
                Column(
                    modifier = imageModifier.background(Color.LightGray.copy(alpha = 0.5f)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LocalImage(
                        imageResource = placeholderImageResource,
                        modifier = Modifier.size(width = (50 * imageAspectRatio).dp, height = 50.dp).padding(bottom = 10.dp),
                        contentScale = ContentScale.Crop
                    )

                    when (state) {
                        is ImagePainter.State.Empty -> Text("There is no image to display", style = SampleTextStyle.subheadline)
                        is ImagePainter.State.Loading -> Text("Loading", style = SampleTextStyle.subheadline)
                        is ImagePainter.State.Error -> Text("Unable to load the remote image", style = SampleTextStyle.subheadline)
                        else -> {}
                    }
                }
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun ImageShowcaseViewPreview() {
    TrikotViewModelDeclarative.initialize(SampleImageProvider())
    ImageShowcaseView(imageShowcaseViewModel = ImageShowcaseViewModelPreview())
}
