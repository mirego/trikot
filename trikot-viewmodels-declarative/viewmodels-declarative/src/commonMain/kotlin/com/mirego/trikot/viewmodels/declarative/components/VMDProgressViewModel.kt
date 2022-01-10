package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.viewmodels.declarative.properties.VMDProgressDetermination
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel

interface VMDProgressViewModel : VMDViewModel {
    val determination: VMDProgressDetermination?
}
