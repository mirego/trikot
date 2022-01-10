package com.trikot.sample.factories

import com.trikot.sample.viewmodels.home.HomeViewModelController

interface ViewModelControllerFactory {
    fun createHome(): HomeViewModelController
}
