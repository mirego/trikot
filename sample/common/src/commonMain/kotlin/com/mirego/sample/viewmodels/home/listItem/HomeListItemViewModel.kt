package com.mirego.sample.viewmodels.home.listItem

import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel

interface HomeListItemViewModel : VMDViewModel {
    val id: String
    val name: VMDTextViewModel
}
