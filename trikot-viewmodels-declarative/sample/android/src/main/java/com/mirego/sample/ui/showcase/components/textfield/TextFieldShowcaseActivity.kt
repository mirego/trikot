package com.mirego.sample.ui.showcase.components.textfield

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import com.mirego.sample.ui.BaseSampleActivity
import com.mirego.sample.viewmodels.showcase.components.textfield.TextFieldShowcaseNavigationDelegate
import com.mirego.sample.viewmodels.showcase.components.textfield.TextFieldShowcaseViewModel
import com.mirego.sample.viewmodels.showcase.components.textfield.TextFieldShowcaseViewModelController

class TextFieldShowcaseActivity : BaseSampleActivity<TextFieldShowcaseViewModelController, TextFieldShowcaseViewModel, TextFieldShowcaseNavigationDelegate>(), TextFieldShowcaseNavigationDelegate {

    override val viewModelController: TextFieldShowcaseViewModelController by lazy {
        getViewModelController(TextFieldShowcaseViewModelController::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TextFieldShowcaseView(textFieldShowcaseViewModel = viewModel)
        }
    }

    override fun close() {
        finish()
    }

    companion object {
        fun intent(context: Context) = Intent(context, TextFieldShowcaseActivity::class.java)
    }
}
