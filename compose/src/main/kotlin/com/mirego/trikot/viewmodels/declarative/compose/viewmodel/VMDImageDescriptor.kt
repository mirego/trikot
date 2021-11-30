package com.mirego.trikot.viewmodels.declarative.compose.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageDescriptor

@Composable
fun VMDImageDescriptor(
    modifier: Modifier = Modifier,
    imageDescriptor: VMDImageDescriptor,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    contentDescription: String = ""
) {
    if (imageDescriptor is VMDImageDescriptor.Local) {
        LocalImage(
            modifier = modifier,
            imageResource = imageDescriptor.imageResource,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter,
            contentDescription = contentDescription
        )
    } else if (imageDescriptor is VMDImageDescriptor.Remote) {
        RemoteImage(
            modifier = modifier,
            imageUrl = imageDescriptor.url,
            placeholderImage = imageDescriptor.placeholderImageResource,
            alignment = alignment,
            contentScale = contentScale,
            colorFilter = colorFilter,
            contentDescription = contentDescription
        )
    }
}