package com.mirego.sample.ui.showcase.components.progress

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import com.mirego.sample.ui.BaseSampleActivity
import com.mirego.sample.viewmodels.showcase.components.progress.ProgressShowcaseNavigationDelegate
import com.mirego.sample.viewmodels.showcase.components.progress.ProgressShowcaseViewModel
import com.mirego.sample.viewmodels.showcase.components.progress.ProgressShowcaseViewModelController

class ProgressShowcaseActivity : BaseSampleActivity<ProgressShowcaseViewModelController, ProgressShowcaseViewModel, ProgressShowcaseNavigationDelegate>(), ProgressShowcaseNavigationDelegate {

    override val viewModelController: ProgressShowcaseViewModelController by lazy {
        getViewModelController(ProgressShowcaseViewModelController::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProgressShowcaseView(progressShowcaseViewModel = viewModel)
        }
    }

    override fun close() {
        finish()
    }

    companion object {
        fun intent(context: Context) = Intent(context, ProgressShowcaseActivity::class.java)
    }
}
