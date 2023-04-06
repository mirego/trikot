package com.mirego.sample.viewmodels.home

import com.mirego.trikot.viewmodels.declarative.components.VMDListViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDSnackbarViewData
import com.mirego.trikot.viewmodels.declarative.extension.VMDFlow
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel

interface HomeViewModel : VMDViewModel {
    val title: String
    val sections: VMDListViewModel<HomeSectionViewModel>
    val snackbar: VMDFlow<VMDSnackbarViewData>
}
