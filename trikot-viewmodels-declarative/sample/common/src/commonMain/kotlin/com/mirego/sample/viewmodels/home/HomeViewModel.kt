package com.mirego.sample.viewmodels.home

import com.mirego.trikot.viewmodels.declarative.components.VMDButtonViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDListViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.content.VMDIdentifiableContentAbstract
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel

interface HomeViewModel : VMDViewModel {
    val title: VMDTextViewModel
    val items: VMDListViewModel<VMDIdentifiableContentAbstract<VMDButtonViewModel<VMDTextContent>>>
}
