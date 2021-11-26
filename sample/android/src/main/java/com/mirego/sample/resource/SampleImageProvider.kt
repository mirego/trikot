package com.mirego.sample.resource

import android.content.Context
import com.mirego.trikot.viewmodels.declarative.configuration.VMDImageProvider
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageResource

class SampleImageProvider : VMDImageProvider {
    override fun resourceIdForResource(resource: VMDImageResource, context: Context): Int? {
        return null
    }
}
