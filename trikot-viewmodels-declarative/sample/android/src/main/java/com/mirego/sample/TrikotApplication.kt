package com.mirego.sample

import android.app.Application
import com.mirego.sample.factories.SampleViewModelControllerFactoryImpl
import com.mirego.sample.resource.SampleImageProvider
import com.mirego.trikot.http.HttpConfiguration
import com.mirego.trikot.http.android.AndroidConnectivityPublisher
import com.mirego.trikot.http.android.requestFactory.KtorHttpRequestFactory
import com.mirego.trikot.kword.android.AndroidKWord
import com.mirego.trikot.streams.reactive.distinctUntilChanged
import com.mirego.trikot.viewmodels.declarative.configuration.TrikotViewModelDeclarative
import com.mirego.trikot.viewmodels.declarative.controller.VMDViewModelControllerFactory
import com.mirego.trikot.viewmodels.declarative.controller.factory.ViewModelControllerFactoryProvidingApplication
import kotlin.time.ExperimentalTime

class TrikotApplication : Application(), ViewModelControllerFactoryProvidingApplication {

    @ExperimentalTime
    override fun onCreate() {
        super.onCreate()

        HttpConfiguration.connectivityPublisher = AndroidConnectivityPublisher(this).distinctUntilChanged()
        HttpConfiguration.httpRequestFactory = KtorHttpRequestFactory()
        TrikotViewModelDeclarative.initialize(SampleImageProvider())
        AndroidKWord.setCurrentLanguageCode("en")
    }

    override val viewModelControllerFactory: VMDViewModelControllerFactory =
        SampleViewModelControllerFactoryImpl()
}
