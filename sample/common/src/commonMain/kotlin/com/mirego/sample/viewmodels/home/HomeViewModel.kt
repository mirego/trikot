package com.mirego.sample.viewmodels.home

import com.mirego.sample.viewmodels.home.listItem.HomeListItemViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel

interface HomeViewModel : VMDViewModel {
    val title: VMDTextViewModel
    val items: List<HomeListItemViewModel>
}
