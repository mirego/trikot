package com.mirego.sample.resource

import android.content.Context
import com.mirego.sample.resources.SampleImageResource
import com.mirego.trikot.viewmodels.declarative.configuration.VMDImageProvider
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageResource
import com.mirego.trikot.viewmodels.declarative.properties.VMDNoImageResource
import com.mirego.vmd.sample.android.R

class SampleImageProvider : VMDImageProvider {
    override fun resourceIdForResource(resource: VMDImageResource, context: Context): Int? {
        return when (resource) {
            is VMDNoImageResource -> null
            is SampleImageResource -> mapSampleImageResource(resource)
            else -> null
        }
    }

    private fun mapSampleImageResource(resource: VMDImageResource): Int? {
        return when (resource) {
            SampleImageResource.ICON_CLOSE -> R.drawable.ic_close
            SampleImageResource.IMAGE_BRIDGE -> R.drawable.bridge
            SampleImageResource.IMAGE_PLACEHOLDER -> R.drawable.placeholder
            else -> null
        }
    }
}
