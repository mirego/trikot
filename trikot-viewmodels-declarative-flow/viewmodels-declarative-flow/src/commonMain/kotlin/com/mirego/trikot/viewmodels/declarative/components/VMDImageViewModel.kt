package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.viewmodels.declarative.properties.VMDImageDescriptor
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel

interface VMDImageViewModel : VMDViewModel {
    val image: VMDImageDescriptor
}
