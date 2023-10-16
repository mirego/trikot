package com.mirego.sample.ui.showcase.components.text

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import com.mirego.sample.viewmodels.showcase.components.text.TextShowcaseNavigationDelegate
import com.mirego.sample.viewmodels.showcase.components.text.TextShowcaseViewModel
import com.mirego.sample.viewmodels.showcase.components.text.TextShowcaseViewModelController
import com.mirego.trikot.viewmodels.declarative.controller.ViewModelActivity

class TextShowcaseActivity : ViewModelActivity<TextShowcaseViewModelController, TextShowcaseViewModel, TextShowcaseNavigationDelegate>(), TextShowcaseNavigationDelegate {

    override val viewModelController: TextShowcaseViewModelController by lazy {
        getViewModelController(TextShowcaseViewModelController::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TextShowcaseView(textShowcaseViewModel = viewModel)
        }
    }

    override fun showMessage(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun close() {
        finish()
    }

    companion object {
        fun intent(context: Context) = Intent(context, TextShowcaseActivity::class.java)
    }
}
