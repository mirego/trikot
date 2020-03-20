package com.mirego.trikot.viewmodels

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.util.StateSet
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.mirego.trikot.viewmodels.properties.Color
import com.mirego.trikot.viewmodels.properties.StateSelector
import com.mirego.trikot.viewmodels.resource.ImageResource
import com.mirego.trikot.viewmodels.resources.ImageViewModelResourceManager

fun StateSelector<ImageResource>.asDrawable(
    context: Context,
    tintColor: StateSelector<Color>?
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
    return ImageViewModelResourceManager.provider.resourceIdFromResource(this, context)
}

fun ImageResource.asDrawable(
    context: Context,
    tintColors: StateSelector<Color>? = null
): Drawable? {
    return resourceId(context)?.let { resourceId -> ContextCompat.getDrawable(context, resourceId) }?.also { drawable ->
        if (tintColors?.isEmpty == false) {
            DrawableCompat.setTintList(drawable.mutate(), tintColors.toColorStateList())
        }
    }
}
