package com.mirego.sample.ui.showcase.toggle

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import com.mirego.sample.viewmodels.showcase.toggle.ToggleShowcaseNavigationDelegate
import com.mirego.sample.viewmodels.showcase.toggle.ToggleShowcaseViewModel
import com.mirego.sample.viewmodels.showcase.toggle.ToggleShowcaseViewModelController
import com.mirego.trikot.viewmodels.declarative.controller.ViewModelActivity

class ToggleShowcaseActivity : ViewModelActivity<ToggleShowcaseViewModelController, ToggleShowcaseViewModel, ToggleShowcaseNavigationDelegate>(), ToggleShowcaseNavigationDelegate {

    override val viewModelController: ToggleShowcaseViewModelController by lazy {
        getViewModelController(ToggleShowcaseViewModelController::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToggleShowcaseView(toggleShowcaseViewModel = viewModel)
        }
    }

    override fun close() {
        finish()
    }

    companion object {
        fun intent(context: Context) = Intent(context, ToggleShowcaseActivity::class.java)
    }
}
