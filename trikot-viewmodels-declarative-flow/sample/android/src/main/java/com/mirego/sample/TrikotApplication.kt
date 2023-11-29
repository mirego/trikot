package com.mirego.sample

import android.app.Application
import com.mirego.sample.factories.SampleViewModelControllerFactoryImpl
import com.mirego.sample.resource.SampleImageProvider
import com.mirego.sample.resource.SampleTextStyleProvider
import com.mirego.trikot.kword.android.AndroidKWord
import com.mirego.trikot.viewmodels.declarative.configuration.TrikotViewModelDeclarative
import com.mirego.trikot.viewmodels.declarative.controller.VMDViewModelControllerFactory
import com.mirego.trikot.viewmodels.declarative.controller.factory.ViewModelControllerFactoryProvidingApplication
import okio.FileSystem

class TrikotApplication : Application(), ViewModelControllerFactoryProvidingApplication {

    override fun onCreate() {
        super.onCreate()

        TrikotViewModelDeclarative.initialize(SampleImageProvider(), SampleTextStyleProvider())
        AndroidKWord.setCurrentLanguageCode("en", FileSystem.SYSTEM)
    }

    override val viewModelControllerFactory: VMDViewModelControllerFactory =
        SampleViewModelControllerFactoryImpl()
}
