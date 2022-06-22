package com.mirego.sample.viewmodels.home

import com.mirego.trikot.viewmodels.declarative.components.VMDButtonViewModel
import com.mirego.trikot.viewmodels.declarative.content.VMDIdentifiableContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel

interface HomeSectionElementViewModel : VMDViewModel, VMDIdentifiableContent {
    val button: VMDButtonViewModel<VMDTextContent>
}
