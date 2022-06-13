package com.mirego.sample.viewmodels.showcase.components.textfield

import com.mirego.sample.viewmodels.showcase.ShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDButtonViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDTextFieldViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent

interface TextFieldShowcaseViewModel : ShowcaseViewModel {
    val textField: VMDTextFieldViewModel
    val characterCountText: VMDTextViewModel
    val clearButton: VMDButtonViewModel<VMDTextContent>
}
