package com.trikot.viewmodels.sample.navigation

import com.mirego.trikot.viewmodels.ListItemViewModel
import com.mirego.trikot.viewmodels.ListViewModel
import com.trikot.viewmodels.sample.viewmodels.home.ButtonsViewModel
import com.trikot.viewmodels.sample.viewmodels.home.ImagesViewModel
import com.trikot.viewmodels.sample.viewmodels.home.InputTextViewModel
import com.trikot.viewmodels.sample.viewmodels.home.LabelsViewModel
import com.trikot.viewmodels.sample.viewmodels.home.PickersViewModel
import com.trikot.viewmodels.sample.viewmodels.home.SlidersViewModel
import com.trikot.viewmodels.sample.viewmodels.home.SwitchesViewModel
import com.trikot.viewmodels.sample.viewmodels.home.ViewsViewModel

interface NavigationDelegate {
    fun navigateTo(destination: Destination)
    fun showAlert(text: String)
}

enum class Destination(val getViewModel: (NavigationDelegate) -> ListViewModel<ListItemViewModel>) {
    VIEWS({ ViewsViewModel().apply { navigationDelegate = it } }),
    LABELS({ LabelsViewModel().apply { navigationDelegate = it } }),
    BUTTONS({ ButtonsViewModel().apply { navigationDelegate = it } }),
    IMAGES({ ImagesViewModel().apply { navigationDelegate = it } }),
    INPUT_TEXT({ InputTextViewModel().apply { navigationDelegate = it } }),
    SLIDERS({ SlidersViewModel().apply { navigationDelegate = it } }),
    SWITCHES({ SwitchesViewModel().apply { navigationDelegate = it } }),
    PICKERS({ PickersViewModel().apply { navigationDelegate = it } })
}
