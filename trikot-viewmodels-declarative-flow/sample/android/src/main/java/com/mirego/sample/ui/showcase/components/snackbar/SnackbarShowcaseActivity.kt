package com.mirego.sample.ui.showcase.components.snackbar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import com.mirego.sample.ui.BaseSampleActivity
import com.mirego.sample.viewmodels.showcase.components.snackbar.SnackbarShowcaseNavigationDelegate
import com.mirego.sample.viewmodels.showcase.components.snackbar.SnackbarShowcaseViewModel
import com.mirego.sample.viewmodels.showcase.components.snackbar.SnackbarShowcaseViewModelController

class SnackbarShowcaseActivity : BaseSampleActivity<SnackbarShowcaseViewModelController, SnackbarShowcaseViewModel, SnackbarShowcaseNavigationDelegate>(), SnackbarShowcaseNavigationDelegate {
    override val viewModelController: SnackbarShowcaseViewModelController by lazy {
        getViewModelController(SnackbarShowcaseViewModelController::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SnackbarShowcaseView(snackbarShowcaseViewModel = viewModel)
        }
    }

    override fun close() {
        finish()
    }

    companion object {
        fun intent(context: Context) = Intent(context, SnackbarShowcaseActivity::class.java)
    }
}
