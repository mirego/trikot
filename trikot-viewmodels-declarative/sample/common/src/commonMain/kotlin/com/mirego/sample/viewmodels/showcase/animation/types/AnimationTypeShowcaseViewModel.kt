package com.mirego.sample.viewmodels.showcase.animation.types

import com.mirego.trikot.viewmodels.declarative.components.VMDButtonViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel

interface AnimationTypeShowcaseViewModel: VMDViewModel {
    val title: VMDTextViewModel
    val isTrailing: Boolean
    val animateButton: VMDButtonViewModel<VMDTextContent>
}