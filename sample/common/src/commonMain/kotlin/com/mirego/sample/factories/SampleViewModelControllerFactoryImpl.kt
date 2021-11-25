package com.mirego.sample.factories

import com.mirego.sample.viewmodels.home.HomeViewModelController

class SampleViewModelControllerFactoryImpl : SampleViewModelControllerFactory {
    override fun home(): HomeViewModelController {
        return HomeViewModelController()
    }
}
