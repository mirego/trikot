package com.trikot.viewmodels.sample.resource

import android.content.Context
import com.mirego.trikot.viewmodels.resource.ImageResource
import com.mirego.trikot.viewmodels.resources.ImageViewModelResourceProvider
import com.trikot.viewmodels.sample.R

class SampleImageResourceProvider : ImageViewModelResourceProvider {
    override fun resourceIdFromResource(resource: ImageResource, context: Context): Int? {
        if (resource == ImageResources.ICON) {
            return R.drawable.icon
        }
        return null
    }
}
