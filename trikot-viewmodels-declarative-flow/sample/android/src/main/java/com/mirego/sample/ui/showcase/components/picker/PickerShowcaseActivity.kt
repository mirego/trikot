package com.mirego.sample.ui.showcase.components.picker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import com.mirego.sample.ui.BaseSampleActivity
import com.mirego.sample.viewmodels.showcase.components.picker.PickerShowcaseNavigationDelegate
import com.mirego.sample.viewmodels.showcase.components.picker.PickerShowcaseViewModel
import com.mirego.sample.viewmodels.showcase.components.picker.PickerShowcaseViewModelController

class PickerShowcaseActivity : BaseSampleActivity<PickerShowcaseViewModelController, PickerShowcaseViewModel, PickerShowcaseNavigationDelegate>(), PickerShowcaseNavigationDelegate {

    override val viewModelController: PickerShowcaseViewModelController by lazy {
        getViewModelController(PickerShowcaseViewModelController::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PickerShowcaseView(pickerShowcaseViewModel = viewModel)
        }
    }

    override fun close() {
        finish()
    }

    companion object {
        fun intent(context: Context) = Intent(context, PickerShowcaseActivity::class.java)
    }
}
