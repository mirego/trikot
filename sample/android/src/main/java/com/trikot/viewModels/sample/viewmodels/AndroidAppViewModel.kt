package com.trikot.viewmodels.sample.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.trikot.viewmodels.sample.navigation.NavigationDelegate
import com.trikot.viewmodels.sample.viewmodels.home.HomeViewModelImpl
import com.trikot.viewmodels.sample.viewmodels.home.ListViewModel

class AndroidAppViewModel(application: Application) : AndroidViewModel(application) {
    fun getVm(navigationDelegate: NavigationDelegate): ListViewModel = HomeViewModelImpl(navigationDelegate)
}
