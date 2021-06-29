package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.viewmodels.declarative.properties.ImageDescriptor
import com.mirego.trikot.viewmodels.declarative.viewmodel.ViewModel

interface ImageViewModel : ViewModel {
    val image: ImageDescriptor
}
