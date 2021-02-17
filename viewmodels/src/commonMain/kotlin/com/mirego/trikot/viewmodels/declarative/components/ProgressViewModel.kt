package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.viewmodels.declarative.ViewModel
import com.mirego.trikot.viewmodels.declarative.properties.ProgressDetermination

interface ProgressViewModel : ViewModel {
    val determination: ProgressDetermination?
}
