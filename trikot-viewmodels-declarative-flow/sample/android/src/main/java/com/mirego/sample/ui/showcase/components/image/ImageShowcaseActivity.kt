package com.mirego.sample.ui.showcase.components.image

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import com.mirego.sample.viewmodels.showcase.components.image.ImageShowcaseNavigationDelegate
import com.mirego.sample.viewmodels.showcase.components.image.ImageShowcaseViewModel
import com.mirego.sample.viewmodels.showcase.components.image.ImageShowcaseViewModelController
import com.mirego.trikot.viewmodels.declarative.controller.ViewModelActivity

class ImageShowcaseActivity : ViewModelActivity<ImageShowcaseViewModelController, ImageShowcaseViewModel, ImageShowcaseNavigationDelegate>(), ImageShowcaseNavigationDelegate {

    override val viewModelController: ImageShowcaseViewModelController by lazy {
        getViewModelController(ImageShowcaseViewModelController::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageShowcaseView(imageShowcaseViewModel = viewModel)
        }
    }

    override fun close() {
        finish()
    }

    companion object {
        fun intent(context: Context) = Intent(context, ImageShowcaseActivity::class.java)
    }
}
