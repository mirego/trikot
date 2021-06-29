package com.mirego.sample.viewmodels.home

import com.mirego.trikot.viewmodels.declarative.controller.ViewModelController

class HomeViewModelController : ViewModelController<HomeViewModel, HomeNavigationDelegate>() {
    override val viewModel = HomeViewModelImpl(cancellableManager).apply {
    }
}
