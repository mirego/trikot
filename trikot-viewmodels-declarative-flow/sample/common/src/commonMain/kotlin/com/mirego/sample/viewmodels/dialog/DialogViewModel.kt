package com.mirego.sample.viewmodels.dialog

import com.mirego.trikot.viewmodels.declarative.components.VMDButtonViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel

interface DialogViewModel : VMDViewModel {
    val message: VMDTextViewModel
    val leftButton: VMDButtonViewModel<VMDTextContent>
    val rightButton: VMDButtonViewModel<VMDTextContent>
}
