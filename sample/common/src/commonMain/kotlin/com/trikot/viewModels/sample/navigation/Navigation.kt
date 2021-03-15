package com.trikot.viewmodels.sample.navigation

import com.mirego.trikot.viewmodels.ListItemViewModel
import com.mirego.trikot.viewmodels.ListViewModel
import com.trikot.viewmodels.sample.viewmodels.home.ButtonsViewModel
import com.trikot.viewmodels.sample.viewmodels.home.ImagesViewModel
import com.trikot.viewmodels.sample.viewmodels.home.InputTextViewModel
import com.trikot.viewmodels.sample.viewmodels.home.LabelsViewModel
import com.trikot.viewmodels.sample.viewmodels.home.SlidersViewModel
import com.trikot.viewmodels.sample.viewmodels.home.SwitchesViewModel
import com.trikot.viewmodels.sample.viewmodels.home.ViewsViewModel

interface NavigationDelegate {
    fun navigateTo(destination: Destination)
    fun showAlert(text: String)
}

enum class Destination(val getViewModel: (NavigationDelegate) -> ListViewModel<ListItemViewModel>) {
    VIEWS({ ViewsViewModel(it) }),
    LABELS({ LabelsViewModel(it) }),
    BUTTONS({ ButtonsViewModel(it) }),
    IMAGES({ ImagesViewModel(it) }),
    INPUT_TEXT({ InputTextViewModel(it) }),
    SLIDERS({ SlidersViewModel(it) }),
    SWITCHES({ SwitchesViewModel(it) })
}
