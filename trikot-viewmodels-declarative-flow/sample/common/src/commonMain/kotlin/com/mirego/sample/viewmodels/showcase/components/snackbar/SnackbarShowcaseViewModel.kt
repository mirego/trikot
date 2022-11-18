package com.mirego.sample.viewmodels.showcase.components.snackbar

import com.mirego.sample.viewmodels.showcase.ShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDButtonViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDSnackbarViewData
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent
import com.mirego.trikot.viewmodels.declarative.extension.VMDFlow

interface SnackbarShowcaseViewModel : ShowcaseViewModel {
    val snackbar: VMDFlow<VMDSnackbarViewData>
    val buttons: List<VMDButtonViewModel<VMDTextContent>>
}
