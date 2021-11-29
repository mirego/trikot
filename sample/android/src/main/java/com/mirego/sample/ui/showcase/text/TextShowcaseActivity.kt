package com.mirego.sample.ui.showcase.text

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import com.mirego.sample.viewmodels.showcase.text.TextShowcaseNavigationDelegate
import com.mirego.sample.viewmodels.showcase.text.TextShowcaseViewModel
import com.mirego.sample.viewmodels.showcase.text.TextShowcaseViewModelController
import com.mirego.trikot.viewmodels.declarative.controller.ViewModelActivity

class TextShowcaseActivity : ViewModelActivity<TextShowcaseViewModelController, TextShowcaseViewModel, TextShowcaseNavigationDelegate>(), TextShowcaseNavigationDelegate {

    override val viewModelController: TextShowcaseViewModelController by lazy {
        getViewModelController(TextShowcaseViewModelController::class)
    }

    override fun close() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TextShowcaseView(textShowcaseViewModel = viewModel)
        }
    }

    companion object {
        fun intent(context: Context) = Intent(context, TextShowcaseActivity::class.java)
    }
}
