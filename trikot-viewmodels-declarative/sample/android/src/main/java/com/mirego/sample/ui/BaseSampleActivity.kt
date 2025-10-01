package com.mirego.sample.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.mirego.trikot.viewmodels.declarative.controller.VMDNavigationDelegate
import com.mirego.trikot.viewmodels.declarative.controller.VMDViewModelController
import com.mirego.trikot.viewmodels.declarative.controller.ViewModelActivity
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel

abstract class BaseSampleActivity<VMC : VMDViewModelController<VM, N>, VM : VMDViewModel, N : VMDNavigationDelegate> :
    ViewModelActivity<VMC, VM, N>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
    }
}
