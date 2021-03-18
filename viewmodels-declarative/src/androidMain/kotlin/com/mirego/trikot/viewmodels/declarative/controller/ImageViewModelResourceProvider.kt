package com.mirego.trikot.viewmodels.declarative.controller

import android.content.Context
import com.mirego.trikot.viewmodels.declarative.properties.ImageResource

interface ImageViewModelResourceProvider {
    fun resourceIdFromResource(resource: ImageResource, context: Context): Int?
}

object ImageViewModelResourceManager {
    var provider: ImageViewModelResourceProvider =
        DefaultImageViewModelResourceProvider()
}

class DefaultImageViewModelResourceProvider : ImageViewModelResourceProvider {
    override fun resourceIdFromResource(resource: ImageResource, context: Context): Int? {
        return null
    }
}
