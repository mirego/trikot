package com.mirego.sample.viewmodels.showcase.components.image

import com.mirego.sample.viewmodels.showcase.ShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDImageViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageDescriptor

interface ImageShowcaseViewModel : ShowcaseViewModel {
    val localImageTitle: VMDTextViewModel
    val localImage: VMDImageViewModel

    val localImageDescriptorTitle: VMDTextViewModel
    val localImageDescriptor: VMDImageDescriptor

    val remoteImageDescriptorTitle: VMDTextViewModel
    val remoteImageDescriptor: VMDImageDescriptor

    val remoteImageTitle: VMDTextViewModel
    val remoteImage: VMDImageViewModel

    val placeholderImageTitle: VMDTextViewModel
    val placeholderImage: VMDImageViewModel

    val complexPlaceholderImageTitle: VMDTextViewModel
    val complexPlaceholderImage: VMDImageViewModel
}
