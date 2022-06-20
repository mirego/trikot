package com.mirego.trikot.viewmodels.declarative.configuration

import com.mirego.trikot.viewmodels.declarative.properties.VMDImageResource

actual interface VMDImageProvider {
    fun imageNameForResource(imageResource: VMDImageResource): String?
}
