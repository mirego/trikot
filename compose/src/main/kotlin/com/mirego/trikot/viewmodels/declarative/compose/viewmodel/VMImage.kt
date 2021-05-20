package com.mirego.trikot.viewmodels.declarative.compose.viewmodel

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import com.google.accompanist.glide.rememberGlidePainter
import com.google.accompanist.imageloading.ImageLoadState
import com.mirego.trikot.viewmodels.declarative.components.ImageViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.extensions.painterResource
import com.mirego.trikot.viewmodels.declarative.compose.ui.hidden
import com.mirego.trikot.viewmodels.declarative.properties.ImageDescriptor.Local
import com.mirego.trikot.viewmodels.declarative.properties.ImageDescriptor.Remote
import com.mirego.trikot.viewmodels.declarative.properties.ImageResource

@Composable
fun VMImage(
    modifier: Modifier = Modifier,
    viewModel: ImageViewModel,
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
                .hidden(imageViewModel.hidden)
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
                .hidden(imageViewModel.hidden)
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
    imageResource: ImageResource,
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

@Composable
fun RemoteImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    placeholderImage: ImageResource = ImageResource.None,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    colorFilter: ColorFilter? = null,
    contentDescription: String = ""
) {
    val glidePainter = rememberGlidePainter(imageUrl)
    Image(
        painter = glidePainter,
        modifier = modifier,
        alignment = alignment,
        colorFilter = colorFilter,
        contentScale = contentScale,
        contentDescription = contentDescription
    )
    when (glidePainter.loadState) {
        is ImageLoadState.Loading -> LocalImage(imageResource = placeholderImage)
        is ImageLoadState.Error -> LocalImage(imageResource = placeholderImage)
        else -> {
        }
    }
}
