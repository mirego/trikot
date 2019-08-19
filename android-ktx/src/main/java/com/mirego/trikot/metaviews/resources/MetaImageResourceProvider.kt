package com.mirego.trikot.metaviews.resources

import android.content.Context
import com.mirego.trikot.metaviews.resource.ImageResource

interface MetaImageResourceProvider {
    fun resourceIdFromResource(resource: ImageResource, context: Context): Int?
}

object MetaImageResourceManager {
    var provider: MetaImageResourceProvider =
        DefaultMetaImageResourceProvider()
}

class DefaultMetaImageResourceProvider : MetaImageResourceProvider {
    override fun resourceIdFromResource(resource: ImageResource, context: Context): Int? {
        return null
    }
}
