package com.mirego.sample.factories

import com.mirego.sample.viewmodels.home.HomeViewModelController

interface ViewModelControllerFactory {
    fun home(): HomeViewModelController
}
