package com.mirego.sample.ui.showcase.button

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import com.mirego.sample.viewmodels.showcase.components.button.ButtonShowcaseNavigationDelegate
import com.mirego.sample.viewmodels.showcase.components.button.ButtonShowcaseViewModel
import com.mirego.sample.viewmodels.showcase.components.button.ButtonShowcaseViewModelController
import com.mirego.trikot.viewmodels.declarative.controller.ViewModelActivity

class ButtonShowcaseActivity : ViewModelActivity<ButtonShowcaseViewModelController, ButtonShowcaseViewModel, ButtonShowcaseNavigationDelegate>(), ButtonShowcaseNavigationDelegate {

    override val viewModelController: ButtonShowcaseViewModelController by lazy {
        getViewModelController(ButtonShowcaseViewModelController::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ButtonShowcaseView(buttonShowcaseViewModel = viewModel)
        }
    }

    override fun showMessage(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun close() {
        finish()
    }

    companion object {
        fun intent(context: Context) = Intent(context, ButtonShowcaseActivity::class.java)
    }
}
