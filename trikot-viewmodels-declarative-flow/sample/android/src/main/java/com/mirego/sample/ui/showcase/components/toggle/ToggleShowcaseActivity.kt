package com.mirego.sample.ui.showcase.components.toggle

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import com.mirego.sample.ui.BaseSampleActivity
import com.mirego.sample.viewmodels.showcase.components.toggle.ToggleShowcaseNavigationDelegate
import com.mirego.sample.viewmodels.showcase.components.toggle.ToggleShowcaseViewModel
import com.mirego.sample.viewmodels.showcase.components.toggle.ToggleShowcaseViewModelController

class ToggleShowcaseActivity : BaseSampleActivity<ToggleShowcaseViewModelController, ToggleShowcaseViewModel, ToggleShowcaseNavigationDelegate>(), ToggleShowcaseNavigationDelegate {

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
