package com.trikot.metaviews.sample.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.trikot.metaviews.sample.navigation.NavigationDelegate
import com.trikot.metaviews.sample.viewmodels.home.HomeViewModelImpl
import com.trikot.metaviews.sample.viewmodels.home.ListViewModel

class AndroidAppViewModel(application: Application) : AndroidViewModel(application) {
    fun getVm(navigationDelegate: NavigationDelegate): ListViewModel = HomeViewModelImpl(navigationDelegate)
}
