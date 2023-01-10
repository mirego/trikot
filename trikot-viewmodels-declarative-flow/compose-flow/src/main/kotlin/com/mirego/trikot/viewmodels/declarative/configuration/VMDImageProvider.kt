package com.mirego.trikot.viewmodels.declarative.configuration

import android.content.Context
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageResource

interface VMDImageProvider {
    fun resourceIdForResource(resource: VMDImageResource, context: Context): Int?
}
