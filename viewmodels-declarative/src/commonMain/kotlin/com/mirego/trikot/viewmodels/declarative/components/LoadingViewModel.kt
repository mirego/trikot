package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.viewmodels.declarative.viewmodel.ViewModel

interface LoadingViewModel : ViewModel {
    val isLoading: Boolean
}
