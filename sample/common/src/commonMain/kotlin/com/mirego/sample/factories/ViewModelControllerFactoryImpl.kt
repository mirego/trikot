package com.mirego.sample.factories

import com.mirego.sample.viewmodels.home.HomeViewModelController

class ViewModelControllerFactoryImpl : ViewModelControllerFactory {
    override fun home(): HomeViewModelController {
        return HomeViewModelController()
    }
}
