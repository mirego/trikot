package com.mirego.sample.viewmodels.home

import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.content.VMDIdentifiableContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel

interface HomeSectionViewModel : VMDViewModel, VMDIdentifiableContent {
    val title: VMDTextViewModel
    val elements: List<HomeSectionElementViewModel>
}
