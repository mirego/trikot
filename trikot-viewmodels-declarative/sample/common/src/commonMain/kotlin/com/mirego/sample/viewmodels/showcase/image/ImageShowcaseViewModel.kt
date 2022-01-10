package com.mirego.sample.viewmodels.showcase.image

import com.mirego.sample.viewmodels.showcase.ShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDImageViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageDescriptor

interface ImageShowcaseViewModel : ShowcaseViewModel {
    val localImageTitle: VMDTextViewModel
    val remoteImageTitle: VMDTextViewModel
    val localImageDescriptorTitle: VMDTextViewModel
    val remoteImageDescriptorTitle: VMDTextViewModel
    val localImage: VMDImageViewModel
    val remoteImage: VMDImageViewModel
    val localImageDescriptor: VMDImageDescriptor
    val remoteImageDescriptor: VMDImageDescriptor
}
