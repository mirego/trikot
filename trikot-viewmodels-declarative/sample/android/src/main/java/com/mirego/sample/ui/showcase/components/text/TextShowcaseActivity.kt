package com.mirego.sample.ui.showcase.components.text

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import com.mirego.sample.ui.BaseSampleActivity
import com.mirego.sample.viewmodels.showcase.components.text.TextShowcaseNavigationDelegate
import com.mirego.sample.viewmodels.showcase.components.text.TextShowcaseViewModel
import com.mirego.sample.viewmodels.showcase.components.text.TextShowcaseViewModelController

class TextShowcaseActivity : BaseSampleActivity<TextShowcaseViewModelController, TextShowcaseViewModel, TextShowcaseNavigationDelegate>(), TextShowcaseNavigationDelegate {

    override val viewModelController: TextShowcaseViewModelController by lazy {
        getViewModelController(TextShowcaseViewModelController::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TextShowcaseView(textShowcaseViewModel = viewModel)
        }
    }

    override fun close() {
        finish()
    }

    companion object {
        fun intent(context: Context) = Intent(context, TextShowcaseActivity::class.java)
    }
}
