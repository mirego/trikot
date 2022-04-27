package com.mirego.sample.ui.showcase.components.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import com.mirego.sample.viewmodels.showcase.components.list.ListShowcaseNavigationDelegate
import com.mirego.sample.viewmodels.showcase.components.list.ListShowcaseViewModel
import com.mirego.sample.viewmodels.showcase.components.list.ListShowcaseViewModelController
import com.mirego.trikot.viewmodels.declarative.controller.ViewModelActivity

class ListShowcaseActivity : ViewModelActivity<ListShowcaseViewModelController, ListShowcaseViewModel, ListShowcaseNavigationDelegate>(), ListShowcaseNavigationDelegate {

    override val viewModelController: ListShowcaseViewModelController by lazy {
        getViewModelController(ListShowcaseViewModelController::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListShowcaseView(listShowcaseViewModel = viewModel)
        }
    }

    override fun close() {
        finish()
    }

    companion object {
        fun intent(context: Context) = Intent(context, ListShowcaseActivity::class.java)
    }
}
