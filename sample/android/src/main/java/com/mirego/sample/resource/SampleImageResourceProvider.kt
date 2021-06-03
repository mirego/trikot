package com.mirego.sample.resource

import android.content.Context
import com.mirego.trikot.viewmodels.declarative.controller.ImageViewModelResourceProvider
import com.mirego.trikot.viewmodels.declarative.properties.ImageResource

class SampleImageResourceProvider : ImageViewModelResourceProvider {
    override fun resourceIdFromResource(resource: ImageResource, context: Context): Int? {
        return null
    }
}
