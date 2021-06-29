package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.viewmodels.declarative.properties.ProgressDetermination
import com.mirego.trikot.viewmodels.declarative.viewmodel.ViewModel

interface ProgressViewModel : ViewModel {
    val determination: ProgressDetermination?
}
