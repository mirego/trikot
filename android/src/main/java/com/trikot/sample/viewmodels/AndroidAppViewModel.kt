package com.trikot.sample.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.trikot.sample.AndroidBootstrap

class AndroidAppViewModel(application: Application) : AndroidViewModel(application) {
    val vm = AndroidBootstrap.bootstrap.viewModelFactory.sampleViewModel
}
