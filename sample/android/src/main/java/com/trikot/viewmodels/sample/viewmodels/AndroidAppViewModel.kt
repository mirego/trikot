package com.trikot.viewmodels.sample.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mirego.trikot.viewmodels.ListItemViewModel
import com.mirego.trikot.viewmodels.ListViewModel
import com.trikot.viewmodels.sample.navigation.NavigationDelegate
import com.trikot.viewmodels.sample.viewmodels.home.HomeViewModelImpl

class AndroidAppViewModel(application: Application) : AndroidViewModel(application) {
    fun getVm(navigationDelegate: NavigationDelegate): ListViewModel<ListItemViewModel> =
        HomeViewModelImpl().apply { this.navigationDelegate = navigationDelegate }
}
