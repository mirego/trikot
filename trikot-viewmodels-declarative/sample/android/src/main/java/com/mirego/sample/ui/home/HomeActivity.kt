package com.mirego.sample.ui.home

import android.os.Bundle
import androidx.activity.compose.setContent
import com.mirego.sample.ui.showcase.button.ButtonShowcaseActivity
import com.mirego.sample.ui.showcase.image.ImageShowcaseActivity
import com.mirego.sample.ui.showcase.progress.ProgressShowcaseActivity
import com.mirego.sample.ui.showcase.text.TextShowcaseActivity
import com.mirego.sample.ui.showcase.textfield.TextFieldShowcaseActivity
import com.mirego.sample.ui.showcase.toggle.ToggleShowcaseActivity
import com.mirego.sample.viewmodels.home.HomeNavigationDelegate
import com.mirego.sample.viewmodels.home.HomeViewModel
import com.mirego.sample.viewmodels.home.HomeViewModelController
import com.mirego.trikot.viewmodels.declarative.controller.ViewModelActivity

class HomeActivity : ViewModelActivity<HomeViewModelController, HomeViewModel, HomeNavigationDelegate>(), HomeNavigationDelegate {

    override val viewModelController: HomeViewModelController by lazy {
        getViewModelController(HomeViewModelController::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeView(viewModel)
        }
    }

    override fun navigateToTextShowcase() {
        startActivity(TextShowcaseActivity.intent(this))
    }

    override fun navigateToButtonShowcase() {
        startActivity(ButtonShowcaseActivity.intent(this))
    }

    override fun navigateToImageShowcase() {
        startActivity(ImageShowcaseActivity.intent(this))
    }

    override fun navigateToTextFieldShowcase() {
        startActivity(TextFieldShowcaseActivity.intent(this))
    }

    override fun navigateToToggleShowcase() {
        startActivity(ToggleShowcaseActivity.intent(this))
    }

    override fun navigateToProgressShowcase() {
        startActivity(ProgressShowcaseActivity.intent(this))
    }
}
