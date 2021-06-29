package com.mirego.sample.viewmodels.home

import com.mirego.sample.viewmodels.home.listItem.HomeListItemViewModel
import com.mirego.trikot.viewmodels.declarative.components.TextViewModel
import com.mirego.trikot.viewmodels.declarative.viewmodel.ViewModel

interface HomeViewModel : ViewModel {
    val title: TextViewModel
    val items: List<HomeListItemViewModel>
}
