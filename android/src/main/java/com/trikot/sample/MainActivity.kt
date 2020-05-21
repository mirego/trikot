package com.trikot.sample

import com.trikot.sample.common.BaseViewModelActivity
import com.trikot.sample.databinding.ActivityMainBinding
import com.trikot.sample.viewmodels.base.BaseNavigationDelegate
import com.trikot.sample.viewmodels.home.HomeViewModel
import com.trikot.sample.viewmodels.home.HomeViewModelController

class MainActivity : BaseViewModelActivity<BaseNavigationDelegate, HomeViewModel>() {
    override val viewModelController: HomeViewModelController by lazy {
        getViewModelController(
            HomeViewModelController::class
        )
    }

    override val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(
            layoutInflater
        )
    }
}
