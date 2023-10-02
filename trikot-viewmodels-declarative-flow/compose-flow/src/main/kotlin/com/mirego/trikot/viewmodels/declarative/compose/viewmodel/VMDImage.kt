package com.mirego.trikot.viewmodels.declarative.compose.viewmodel

import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Dimension
import coil.size.Scale
import coil.size.Size
import coil.size.SizeResolver
import com.mirego.trikot.viewmodels.declarative.components.VMDImageViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.isOverridingAlpha
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.extensions.painterResource
import com.mirego.trikot.viewmodels.declarative.compose.extensions.vmdModifier
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageDescriptor
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageDescriptor.Local
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageDescriptor.Remote
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapNotNull

private const val TAG = "VMDImage"
private const val MAX_BITMAP_SIZE = 100 * 1024 * 1024 // 100 MB, taken from android.graphics.RecordingCanvas

@Composable
fun VMDImage(
    viewModel: VMDImageViewModel,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    onState: ((AsyncImagePainter.State) -> Unit)? = null,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    allowHardware: Boolean = true
) {
    val imageViewModel by viewModel.observeAsState(excludedProperties = if (modifier.isOverridingAlpha()) listOf(viewModel::isHidden) else emptyList())

    VMDImage(
        imageDescriptor = imageViewModel.image,
        modifier = modifier.vmdModifier(imageViewModel),
        contentDescription = imageViewModel.contentDescription,
        alignment = alignment,
        onState = onState,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
        allowHardware = allowHardware
    )
}

@Composable
fun VMDImage(
    imageDescriptor: VMDImageDescriptor,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    alignment: Alignment = Alignment.Center,
    onState: ((AsyncImagePainter.State) -> Unit)? = null,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    allowHardware: Boolean = true
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
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageDescriptor.url)
                    .allowHardware(allowHardware)
                    .build(),
                contentDescription = contentDescription,
                modifier = modifier,
                transform = imageDescriptor.placeholderImageResource
                    .takeUnless { it == VMDImageResource.None }
                    ?.let { transformOf(painterResource(it)) }
                    ?: AsyncImagePainter.DefaultTransform,
                onState = onState,
                contentScale = contentScale,
                alignment = alignment,
                alpha = alpha,
                colorFilter = colorFilter
            )
        }
    }
}

@Composable
fun VMDImage(
    modifier: Modifier = Modifier,
    viewModel: VMDImageViewModel,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    placeholderContentScale: ContentScale = contentScale,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    allowHardware: Boolean = true,
    placeholder: @Composable (BoxScope.(placeholderImageResource: VMDImageResource, state: AsyncImagePainter.State) -> Unit) = { imageResource, _ ->
        val imageViewModel by viewModel.observeAsState(excludedProperties = if (modifier.isOverridingAlpha()) listOf(viewModel::isHidden) else emptyList())
        RemoteImageDefaultPlaceholder(
            imageResource = imageResource,
            modifier = Modifier.matchParentSize(),
            contentScale = placeholderContentScale,
            colorFilter = colorFilter,
            contentDescription = imageViewModel.contentDescription
        )
    },
    asyncStateCallback: ((AsyncImagePainter.State) -> Unit)? = null
) {
    val imageViewModel by viewModel.observeAsState(excludedProperties = if (modifier.isOverridingAlpha()) listOf(viewModel::isHidden) else emptyList())

    VMDImage(
        modifier = Modifier
            .vmdModifier(viewModel),
        alpha = alpha,
        alignment = alignment,
        contentScale = contentScale,
        colorFilter = colorFilter,
        contentDescription = imageViewModel.contentDescription,
        placeholderContentScale = placeholderContentScale,
        imageDescriptor = imageViewModel.image,
        allowHardware = allowHardware,
        placeholder = placeholder,
        asyncStateCallback = asyncStateCallback
    )
}

@Composable
@Deprecated(
    message = "Use the constructor with custom error and loading instead of custom placeholder",
    replaceWith = ReplaceWith("VMDImage with custom error and loading instead of custom placeholder")
)
fun VMDImage(
    modifier: Modifier = Modifier,
    imageDescriptor: VMDImageDescriptor,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    placeholderContentScale: ContentScale = contentScale,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    contentDescription: String? = null,
    allowHardware: Boolean = true,
    placeholder: @Composable (BoxScope.(placeholderImageResource: VMDImageResource, state: AsyncImagePainter.State) -> Unit) = { imageResource, _ ->
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
fun VMDImage(
    modifier: Modifier = Modifier,
    viewModel: VMDImageViewModel,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    placeholderContentScale: ContentScale = contentScale,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    error: @Composable (placeholderImageResource: VMDImageResource) -> Unit = { imageResource ->
        RemoteImageDefaultPlaceholder(
            imageResource = imageResource,
            modifier = modifier,
            contentScale = placeholderContentScale,
            colorFilter = colorFilter,
            contentDescription = viewModel.contentDescription
        )
    },
    loading: @Composable (placeholderImageResource: VMDImageResource) -> Unit = { _ -> }
) {
    val imageViewModel by viewModel.observeAsState(excludedProperties = if (modifier.isOverridingAlpha()) listOf(viewModel::isHidden) else emptyList())

    VMDImage(
        modifier = modifier
            .vmdModifier(viewModel),
        alpha = alpha,
        alignment = alignment,
        contentScale = contentScale,
        colorFilter = colorFilter,
        contentDescription = imageViewModel.contentDescription,
        placeholderContentScale = placeholderContentScale,
        imageDescriptor = imageViewModel.image,
        error = error,
        loading = loading
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
    contentDescription: String? = null,
    error: @Composable (placeholderImageResource: VMDImageResource) -> Unit = { imageResource ->
        RemoteImageDefaultPlaceholder(
            imageResource = imageResource,
            modifier = modifier,
            contentScale = placeholderContentScale,
            colorFilter = colorFilter,
            contentDescription = contentDescription
        )
    },
    loading: @Composable (placeholderImageResource: VMDImageResource) -> Unit = { _ -> }
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
                error = error,
                loading = loading,
                alignment = alignment,
                contentScale = contentScale,
                colorFilter = colorFilter,
                contentDescription = contentDescription
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
    contentDescription: String? = null
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
    placeholder: @Composable (BoxScope.(placeholderImageResource: VMDImageResource, state: AsyncImagePainter.State) -> Unit),
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    colorFilter: ColorFilter? = null,
    contentDescription: String? = null,
    allowHardware: Boolean = true,
    asyncStateCallback: ((AsyncImagePainter.State) -> Unit)? = null
) {
    val sizeResolver = remember { ConstraintsSizeResolver() }
    val coilPainter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .allowHardware(allowHardware)
            .size(sizeResolver)
            .scale(contentScale.scale)
            .build()
    )

    val state = coilPainter.state
    asyncStateCallback?.invoke(state)

    Box(
        modifier = modifier.then(sizeResolver)
    ) {
        when (state) {
            is AsyncImagePainter.State.Success -> {
                val drawable = state.result.drawable
                if (drawable !is BitmapDrawable || drawable.bitmap.allocationByteCount <= MAX_BITMAP_SIZE) {
                    Image(
                        painter = coilPainter,
                        modifier = Modifier.matchParentSize(),
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
}

@Composable
fun RemoteImage(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    placeholderImage: VMDImageResource = VMDImageResource.None,
    error: @Composable (placeholderImageResource: VMDImageResource) -> Unit,
    loading: @Composable (placeholderImageResource: VMDImageResource) -> Unit,
    alignment: Alignment = Alignment.Center,
    transform: (AsyncImagePainter.State) -> AsyncImagePainter.State = AsyncImagePainter.DefaultTransform,
    onState: ((AsyncImagePainter.State) -> Unit)? = null,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
    contentDescription: String? = null
) {
    var hasLoadingFailed by remember { mutableStateOf(false) }
    LaunchedEffect(imageUrl) {
        hasLoadingFailed = false
    }
    SubcomposeAsyncImage(
        modifier = modifier,
        model = imageUrl,
        contentDescription = contentDescription,
        transform = transform,
        alignment = alignment,
        onState = onState,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
        filterQuality = filterQuality
    ) {
        if (hasLoadingFailed || imageUrl == null) {
            error(placeholderImage)
        } else {
            when (painter.state) {
                is AsyncImagePainter.State.Success -> {
                    hasLoadingFailed = false
                    SubcomposeAsyncImageContent()
                }

                is AsyncImagePainter.State.Loading,
                is AsyncImagePainter.State.Empty -> loading(placeholderImage)

                is AsyncImagePainter.State.Error -> {
                    hasLoadingFailed = true
                    error(placeholderImage)
                }
            }
        }
    }
}

@Composable
private fun RemoteImageDefaultPlaceholder(
    imageResource: VMDImageResource,
    modifier: Modifier = Modifier,
    contentScale: ContentScale,
    colorFilter: ColorFilter?,
    contentDescription: String?
) {
    LocalImage(
        imageResource = imageResource,
        modifier = modifier,
        colorFilter = colorFilter,
        contentScale = contentScale,
        contentDescription = contentDescription
    )
}

private class ConstraintsSizeResolver : SizeResolver, LayoutModifier {

    private val cachedConstraints = MutableStateFlow(Constraints.fixed(0, 0))

    override suspend fun size() = cachedConstraints.mapNotNull(Constraints::toSizeOrNull).first()

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        cachedConstraints.value = constraints

        val placeable = measurable.measure(constraints)
        return layout(placeable.width, placeable.height) {
            placeable.place(0, 0)
        }
    }
}

@Stable
private val ContentScale.scale: Scale
    get() = when (this) {
        ContentScale.Fit, ContentScale.Inside -> Scale.FIT
        else -> Scale.FILL
    }

@Stable
private fun Constraints.toSizeOrNull() = when {
    isZero -> null
    else -> Size(
        width = if (hasBoundedWidth) Dimension(maxWidth) else Dimension.Original,
        height = if (hasBoundedHeight) Dimension(maxHeight) else Dimension.Original
    )
}

@Stable
private fun transformOf(
    placeholder: Painter
): (AsyncImagePainter.State) -> AsyncImagePainter.State = { state ->
    when (state) {
        is AsyncImagePainter.State.Loading -> state.copy(painter = placeholder)
        is AsyncImagePainter.State.Error -> state.copy(painter = placeholder)
        else -> state
    }
}
