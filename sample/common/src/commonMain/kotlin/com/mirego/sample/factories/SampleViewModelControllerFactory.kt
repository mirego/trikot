package com.mirego.sample.factories

import com.mirego.sample.viewmodels.home.HomeViewModelController
import com.mirego.trikot.viewmodels.declarative.controller.VMDViewModelControllerFactory

interface SampleViewModelControllerFactory : VMDViewModelControllerFactory {
    fun home(): HomeViewModelController
}
