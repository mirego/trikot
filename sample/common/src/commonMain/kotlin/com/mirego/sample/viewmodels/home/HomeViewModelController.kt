package com.mirego.sample.viewmodels.home

import com.mirego.trikot.viewmodels.declarative.controller.VMDViewModelController

class HomeViewModelController : VMDViewModelController<HomeViewModel, HomeNavigationDelegate>() {
    override val viewModel = HomeViewModelImpl(cancellableManager).apply {
    }
}
