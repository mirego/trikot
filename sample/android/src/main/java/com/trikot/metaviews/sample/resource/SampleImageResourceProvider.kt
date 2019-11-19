package com.trikot.metaviews.sample.resource

import android.content.Context
import com.mirego.trikot.metaviews.resource.ImageResource
import com.mirego.trikot.metaviews.resources.MetaImageResourceProvider
import com.trikot.metaviews.sample.R

class SampleImageResourceProvider : MetaImageResourceProvider {
    override fun resourceIdFromResource(resource: ImageResource, context: Context): Int? {
        if (resource == ImageResources.ICON) {
            return R.drawable.icon
        }
        return null
    }
}
