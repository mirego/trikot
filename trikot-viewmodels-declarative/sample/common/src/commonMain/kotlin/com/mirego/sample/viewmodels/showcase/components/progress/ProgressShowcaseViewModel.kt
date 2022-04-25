package com.mirego.sample.viewmodels.showcase.components.progress

import com.mirego.sample.viewmodels.showcase.ShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDProgressViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel

interface ProgressShowcaseViewModel : ShowcaseViewModel {
    val linearDeterminateProgressTitle: VMDTextViewModel
    val linearIndeterminateProgressTitle: VMDTextViewModel
    val circularDeterminateProgressTitle: VMDTextViewModel
    val circularIndeterminateProgressTitle: VMDTextViewModel
    val determinateProgress: VMDProgressViewModel
    val indeterminateProgress: VMDProgressViewModel
}
