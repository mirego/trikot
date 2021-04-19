package com.mirego.trikot.viewmodels.declarative.compose.extensions

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import com.mirego.trikot.viewmodels.declarative.controller.ImageViewModelResourceManager
import com.mirego.trikot.viewmodels.declarative.properties.ImageResource

fun ImageResource.resourceId(context: Context): Int? {
    return ImageViewModelResourceManager.provider.resourceIdFromResource(this, context)
}

@Composable
fun painterResource(imageResource: ImageResource?): Painter {
    val context = LocalContext.current
    imageResource?.let { resource ->
        ImageViewModelResourceManager.provider.resourceIdFromResource(resource, context)
            ?.let { id ->
                return androidx.compose.ui.res.painterResource(id = id)
            }
    }
    return BitmapPainter(ImageBitmap(1, 1))
}
