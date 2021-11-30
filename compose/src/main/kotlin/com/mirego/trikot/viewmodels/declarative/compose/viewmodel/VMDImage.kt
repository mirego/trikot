package com.mirego.trikot.viewmodels.declarative.compose.viewmodel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.mirego.trikot.viewmodels.declarative.components.VMDImageViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.hidden
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.extensions.painterResource
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageDescriptor.Local
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageDescriptor.Remote
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageResource

@ExperimentalCoilApi
@Composable
fun VMDImage(
    modifier: Modifier = Modifier,
    viewModel: VMDImageViewModel,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    contentDescription: String = ""
) {
    val imageViewModel by viewModel.observeAsState()
    val image = imageViewModel.image

    if (image is Local) {
        LocalImage(
            modifier = Modifier
                .hidden(imageViewModel.isHidden)
                .then(modifier),
            imageResource = image.imageResource,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter,
            contentDescription = contentDescription
        )
    } else if (image is Remote) {
        RemoteImage(
            modifier = Modifier
                .hidden(imageViewModel.isHidden)
                .then(modifier),
            imageUrl = image.url,
            placeholderImage = image.placeholderImageResource,
            alignment = alignment,
            contentScale = contentScale,
            colorFilter = colorFilter,
            contentDescription = contentDescription
        )
    }
}

@Composable
fun LocalImage(
    imageResource: VMDImageResource,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    contentDescription: String = ""
) {
    Image(
        painter = painterResource(imageResource),
        modifier = modifier,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
        contentDescription = contentDescription
    )
}

@ExperimentalCoilApi
@Composable
fun RemoteImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    placeholderImage: VMDImageResource = VMDImageResource.None,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    colorFilter: ColorFilter? = null,
    contentDescription: String = ""
) {
    val coilPainter = rememberImagePainter(imageUrl)
    Box {
        Image(
            painter = coilPainter,
            modifier = modifier,
            alignment = alignment,
            colorFilter = colorFilter,
            contentScale = contentScale,
            contentDescription = contentDescription
        )
        when (coilPainter.state) {
            is ImagePainter.State.Loading -> LocalImage(
                imageResource = placeholderImage,
                modifier = modifier,
                colorFilter = colorFilter,
                contentScale = contentScale,
                contentDescription = contentDescription
            )
            is ImagePainter.State.Error -> LocalImage(
                imageResource = placeholderImage,
                modifier = modifier,
                colorFilter = colorFilter,
                contentScale = contentScale,
                contentDescription = contentDescription
            )
            else -> {
            }
        }
    }
}
