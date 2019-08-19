package com.trikot.sample.resource

import android.content.Context
import com.mirego.trikot.metaviews.resource.ImageResource
import com.mirego.trikot.metaviews.resources.MetaImageResourceProvider

class SampleImageResourceProvider: MetaImageResourceProvider {
    override fun resourceIdFromResource(resource: ImageResource, context: Context): Int? {
        return null
    }
}
