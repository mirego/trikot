package com.mirego.trikot.viewmodels.declarative.extensions

import android.content.Context
import com.mirego.trikot.viewmodels.declarative.controller.ImageViewModelResourceManager
import com.mirego.trikot.viewmodels.declarative.properties.ImageResource

fun ImageResource.resourceId(context: Context): Int? {
    return ImageViewModelResourceManager.provider.resourceIdFromResource(this, context)
}
