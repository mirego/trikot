package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.viewmodels.declarative.ViewModel
import com.mirego.trikot.viewmodels.declarative.properties.ImageDescriptor

interface ImageViewModel : ViewModel {
    val image: ImageDescriptor
}
