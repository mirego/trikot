package com.mirego.sample.ui.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.tv.material3.ExperimentalTvMaterial3Api
import com.mirego.sample.ui.tv.HomeTvView
import com.mirego.sample.viewmodels.tv.HomeTvNavigationDelegate
import com.mirego.sample.viewmodels.tv.HomeTvViewModel
import com.mirego.sample.viewmodels.tv.HomeTvViewModelController
import com.mirego.trikot.viewmodels.declarative.controller.ViewModelActivity

@OptIn(ExperimentalTvMaterial3Api::class)
class HomeTvActivity : ViewModelActivity<HomeTvViewModelController, HomeTvViewModel, HomeTvNavigationDelegate>(), HomeTvNavigationDelegate {
    override val viewModelController: HomeTvViewModelController by lazy {
        getViewModelController(HomeTvViewModelController::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeTvView(viewModel)
        }
    }
}
