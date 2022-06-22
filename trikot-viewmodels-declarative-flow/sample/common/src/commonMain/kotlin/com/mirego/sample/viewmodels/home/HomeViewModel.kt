package com.mirego.sample.viewmodels.home

import com.mirego.trikot.viewmodels.declarative.components.VMDListViewModel
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel

interface HomeViewModel : VMDViewModel {
    val title: String
    val sections: VMDListViewModel<HomeSectionViewModel>
}
