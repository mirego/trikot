package com.mirego.trikot.viewmodels.declarative.configuration

import com.mirego.trikot.viewmodels.declarative.properties.VMDImageResource
import platform.UIKit.UIImage

actual interface VMDImageProvider {
    fun imageNameForResource(imageResource: VMDImageResource): UIImage?
}
