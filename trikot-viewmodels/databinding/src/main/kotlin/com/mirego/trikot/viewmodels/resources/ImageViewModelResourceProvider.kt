package com.mirego.trikot.viewmodels.resources

import android.content.Context
import com.mirego.trikot.viewmodels.resource.TrikotImageResource

interface ImageViewModelResourceProvider {
    fun resourceIdFromResource(resource: TrikotImageResource, context: Context): Int?
}

object ImageViewModelResourceManager {
    var provider: ImageViewModelResourceProvider =
        DefaulImageViewModelResourceProvider()
}

class DefaulImageViewModelResourceProvider : ImageViewModelResourceProvider {
    override fun resourceIdFromResource(resource: TrikotImageResource, context: Context): Int? {
        return null
    }
}
