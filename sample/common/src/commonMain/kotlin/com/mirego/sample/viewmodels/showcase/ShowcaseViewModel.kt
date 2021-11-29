package com.mirego.sample.viewmodels.showcase

import com.mirego.trikot.viewmodels.declarative.components.VMDButtonViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.content.VMDImageContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel

interface ShowcaseViewModel : VMDViewModel {
    val title: VMDTextViewModel
    val closeButton: VMDButtonViewModel<VMDImageContent>
}
