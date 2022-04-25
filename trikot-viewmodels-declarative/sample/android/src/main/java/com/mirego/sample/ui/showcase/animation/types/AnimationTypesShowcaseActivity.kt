package com.mirego.sample.ui.showcase.animation.types

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import com.mirego.sample.viewmodels.showcase.animation.types.AnimationTypesShowcaseNavigationDelegate
import com.mirego.sample.viewmodels.showcase.animation.types.AnimationTypesShowcaseViewModel
import com.mirego.sample.viewmodels.showcase.animation.types.AnimationTypesShowcaseViewModelController
import com.mirego.trikot.viewmodels.declarative.controller.ViewModelActivity

class AnimationTypesShowcaseActivity :
    ViewModelActivity<AnimationTypesShowcaseViewModelController, AnimationTypesShowcaseViewModel, AnimationTypesShowcaseNavigationDelegate>(), AnimationTypesShowcaseNavigationDelegate {

    override val viewModelController: AnimationTypesShowcaseViewModelController by lazy {
        getViewModelController(AnimationTypesShowcaseViewModelController::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationTypesShowcaseView(animationTypesShowcaseViewModel = viewModel)
        }
    }

    override fun close() {
        finish()
    }

    companion object {
        fun intent(context: Context) = Intent(context, AnimationTypesShowcaseActivity::class.java)
    }
}
