package com.trikot.sample.resource

import android.content.Context
import com.mirego.trikot.viewmodels.resource.ImageResource
import com.mirego.trikot.viewmodels.resources.ImageViewModelResourceProvider

class SampleImageResourceProvider : ImageViewModelResourceProvider {
    override fun resourceIdFromResource(resource: ImageResource, context: Context): Int? {
        return null
    }
}
