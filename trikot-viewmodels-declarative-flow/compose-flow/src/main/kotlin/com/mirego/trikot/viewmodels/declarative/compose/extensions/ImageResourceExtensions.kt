package com.mirego.trikot.viewmodels.declarative.compose.extensions

import android.content.Context
import android.util.TypedValue
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.mirego.trikot.viewmodels.declarative.configuration.TrikotViewModelDeclarative
import com.mirego.trikot.viewmodels.declarative.configuration.VMDImageProviderConfiguration.painterResourceLegacyDrawableSupport
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageResource
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException

fun VMDImageResource.resourceId(context: Context): Int? {
    return TrikotViewModelDeclarative.imageProvider.resourceIdForResource(this, context)
}

@Composable
fun painterResource(imageResource: VMDImageResource?): Painter {
    val context = LocalContext.current
    imageResource?.let { resource ->
        resource.resourceId(context)?.let { id ->
            return if (painterResourceLegacyDrawableSupport) {
                legacyPainterResource(id, context)
            } else {
                androidx.compose.ui.res.painterResource(id = id)
            }
        }
    }
    return BitmapPainter(ImageBitmap(1, 1))
}

@Composable
private fun legacyPainterResource(id: Int, context: Context): Painter {
    return if (isVectorResource(id, context)) {
        // Using standard painterResource for vectors
        androidx.compose.ui.res.painterResource(id = id)
    } else {
        // Using Accompanist for all other types
        rememberDrawablePainter(AppCompatResources.getDrawable(context, id))
    }
}

private fun isVectorResource(id: Int, context: Context): Boolean {
    if (isXmlResource(id, context)) {
        val parser = context.resources.getXml(id)
        return parser.seekToStartTagInternal().name == "vector"
    }
    return false
}

private fun isXmlResource(id: Int, context: Context): Boolean {
    val value = TypedValue()
    context.resources.getValue(id, value, true)
    return value.string?.endsWith(".xml") == true
}

/**
 * Copied from androidx.compose.ui.graphics.vector.compat.XmlPullParser.seekToStartTag(), which is internal
 */
private fun XmlPullParser.seekToStartTagInternal(): XmlPullParser {
    var type = next()
    while (type != XmlPullParser.START_TAG && type != XmlPullParser.END_DOCUMENT) {
        // Empty loop
        type = next()
    }
    if (type != XmlPullParser.START_TAG) {
        throw XmlPullParserException("No start tag found")
    }
    return this
}

