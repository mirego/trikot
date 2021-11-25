package com.mirego.sample.factories

import com.mirego.sample.viewmodels.home.HomeViewModelController
import com.mirego.trikot.viewmodels.declarative.controller.ViewModelControllerFactory

interface SampleViewModelControllerFactory : ViewModelControllerFactory {
    fun home(): HomeViewModelController
}
