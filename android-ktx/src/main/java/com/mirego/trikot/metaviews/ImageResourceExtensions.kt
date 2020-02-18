package com.mirego.trikot.metaviews

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.util.StateSet
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.mirego.trikot.metaviews.properties.Color
import com.mirego.trikot.metaviews.properties.MetaSelector
import com.mirego.trikot.metaviews.resource.ImageResource
import com.mirego.trikot.metaviews.resources.MetaImageResourceManager

fun MetaSelector<ImageResource>.asDrawable(
    context: Context,
    tintColor: MetaSelector<Color>?
): Drawable {
    val stateListDrawable = StateListDrawable()

    this.disabled?.let { imageResource ->
        imageResource.asDrawable(context, tintColor).let { drawable ->
            stateListDrawable.addState(intArrayOf(-android.R.attr.state_enabled), drawable)
        }
    }

    this.highlighted?.let { imageResource ->
        imageResource.asDrawable(context, tintColor).let { drawable ->
            stateListDrawable.addState(intArrayOf(android.R.attr.state_pressed), drawable)
            stateListDrawable.addState(intArrayOf(android.R.attr.state_focused), drawable)
        }
    }

    this.selected?.let { imageResource ->
        imageResource.asDrawable(context, tintColor).let { drawable ->
            stateListDrawable.addState(intArrayOf(android.R.attr.state_selected), drawable)
        }
    }

    this.default?.let { imageResource ->
        imageResource.asDrawable(context, tintColor).let { drawable ->
            stateListDrawable.addState(StateSet.WILD_CARD, drawable)
        }
    }

    return stateListDrawable
}

fun ImageResource.resourceId(context: Context): Int? {
    return MetaImageResourceManager.provider.resourceIdFromResource(this, context)
}

fun ImageResource.asDrawable(
    context: Context,
    tintColors: MetaSelector<Color>? = null
): Drawable? {
    return resourceId(context)?.let { resourceId -> ContextCompat.getDrawable(context, resourceId) }?.also { drawable ->
        if (tintColors?.hasAnyValue == true) {
            DrawableCompat.setTintList(drawable.mutate(), tintColors.toColorStateList())
        }
    }
}
