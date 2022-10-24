package com.mirego.trikot.viewmodels.declarative.compose.viewmodel

import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.mirego.trikot.viewmodels.declarative.components.VMDImageViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.hidden
import com.mirego.trikot.viewmodels.declarative.compose.extensions.isOverridingAlpha
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.extensions.painterResource
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageDescriptor
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageDescriptor.Local
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageDescriptor.Remote
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageResource

private const val TAG = "VMDImage"
private const val MAX_BITMAP_SIZE = 100 * 1024 * 1024 // 100 MB, taken from android.graphics.RecordingCanvas

@Composable
fun VMDImage(
    modifier: Modifier = Modifier,
    viewModel: VMDImageViewModel,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    placeholderContentScale: ContentScale = contentScale,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    contentDescription: String = "",
    allowHardware: Boolean = true,
    placeholder: @Composable ((placeholderImageResource: VMDImageResource, state: AsyncImagePainter.State) -> Unit) = { imageResource, state ->
        RemoteImageDefaultPlaceholder(
            imageResource = imageResource,
            modifier = modifier,
            contentScale = placeholderContentScale,
            colorFilter = colorFilter,
            contentDescription = contentDescription
        )
    },
    asyncStateCallback: ((AsyncImagePainter.State) -> Unit)? = null
) {
    val imageViewModel by viewModel.observeAsState(excludedProperties = if (modifier.isOverridingAlpha()) listOf(viewModel::isHidden) else emptyList())

    VMDImage(
        modifier = modifier.hidden(imageViewModel.isHidden),
        alpha = alpha,
        alignment = alignment,
        contentScale = contentScale,
        colorFilter = colorFilter,
        contentDescription = contentDescription,
        placeholderContentScale = placeholderContentScale,
        imageDescriptor = imageViewModel.image,
        allowHardware = allowHardware,
        placeholder = placeholder,
        asyncStateCallback = asyncStateCallback
    )
}

@Composable
fun VMDImage(
    modifier: Modifier = Modifier,
    imageDescriptor: VMDImageDescriptor,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    placeholderContentScale: ContentScale = contentScale,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    contentDescription: String = "",
    allowHardware: Boolean = true,
    placeholder: @Composable ((placeholderImageResource: VMDImageResource, state: AsyncImagePainter.State) -> Unit) = { imageResource, state ->
        RemoteImageDefaultPlaceholder(
            imageResource = imageResource,
            modifier = modifier,
            contentScale = placeholderContentScale,
            colorFilter = colorFilter,
            contentDescription = contentDescription
        )
    },
    asyncStateCallback: ((AsyncImagePainter.State) -> Unit)? = null
) {
    when (imageDescriptor) {
        is Local -> {
            LocalImage(
                modifier = modifier,
                imageResource = imageDescriptor.imageResource,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter,
                contentDescription = contentDescription
            )
        }
        is Remote -> {
            RemoteImage(
                modifier = modifier,
                imageUrl = imageDescriptor.url,
                placeholderImage = imageDescriptor.placeholderImageResource,
                alignment = alignment,
                contentScale = contentScale,
                colorFilter = colorFilter,
                contentDescription = contentDescription,
                allowHardware = allowHardware,
                placeholder = placeholder,
                asyncStateCallback = asyncStateCallback
            )
        }
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

@Composable
fun RemoteImage(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    placeholderImage: VMDImageResource = VMDImageResource.None,
    placeholder: @Composable ((placeholderImageResource: VMDImageResource, state: AsyncImagePainter.State) -> Unit),
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    colorFilter: ColorFilter? = null,
    contentDescription: String = "",
    allowHardware: Boolean = true,
    asyncStateCallback: ((AsyncImagePainter.State) -> Unit)? = null
) {
    val coilPainter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .allowHardware(allowHardware)
            .apply { if (imageUrl != null) size(Size.ORIGINAL) }
            .build()
    )

    val state = coilPainter.state
    asyncStateCallback?.invoke(state)

    when (state) {
        is AsyncImagePainter.State.Success -> {
            val drawable = state.result.drawable
            if (drawable !is BitmapDrawable || drawable.bitmap.allocationByteCount <= MAX_BITMAP_SIZE) {
                Image(
                    painter = coilPainter,
                    modifier = modifier,
                    alignment = alignment,
                    colorFilter = colorFilter,
                    contentScale = contentScale,
                    contentDescription = contentDescription
                )
            } else {
                Log.e(TAG, "Unable to load bitmap: size too large (${drawable.bitmap.allocationByteCount})")
                placeholder(placeholderImage, state)
            }
        }
        else -> placeholder(placeholderImage, state)
    }
}

@Composable
private fun RemoteImageDefaultPlaceholder(
    imageResource: VMDImageResource,
    modifier: Modifier = Modifier,
    contentScale: ContentScale,
    colorFilter: ColorFilter?,
    contentDescription: String
) {
    LocalImage(
        imageResource = imageResource,
        modifier = modifier,
        colorFilter = colorFilter,
        contentScale = contentScale,
        contentDescription = contentDescription
    )
}
