package com.mirego.sample.viewmodels.home.listItem

import com.mirego.trikot.viewmodels.declarative.components.TextViewModel
import com.mirego.trikot.viewmodels.declarative.viewmodel.ViewModel

interface HomeListItemViewModel : ViewModel {
    val id: String
    val name: TextViewModel
}
