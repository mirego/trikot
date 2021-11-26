package com.mirego.trikot.viewmodels.declarative.compose.extensions

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import com.mirego.trikot.viewmodels.declarative.configuration.TrikotViewModelDeclarative
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageResource

fun VMDImageResource.resourceId(context: Context): Int? {
    return TrikotViewModelDeclarative.imageProvider.resourceIdForResource(this, context)
}

@Composable
fun painterResource(imageResource: VMDImageResource?): Painter {
    val context = LocalContext.current
    imageResource?.let { resource ->
        resource.resourceId(context)?.let { id ->
            return androidx.compose.ui.res.painterResource(id = id)
        }
    }
    return BitmapPainter(ImageBitmap(1, 1))
}
