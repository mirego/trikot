package com.mirego.sample

import android.app.Application
import com.mirego.sample.factories.SampleViewModelControllerFactoryImpl
import com.mirego.trikot.http.HttpConfiguration
import com.mirego.trikot.http.android.AndroidConnectivityPublisher
import com.mirego.trikot.http.android.requestFactory.KtorHttpRequestFactory
import com.mirego.trikot.streams.reactive.distinctUntilChanged
import com.mirego.trikot.viewmodels.declarative.controller.ViewModelControllerFactory
import com.mirego.trikot.viewmodels.declarative.controller.factory.ViewModelControllerFactoryProvidingApplication
import kotlin.time.ExperimentalTime

class TrikotApplication : Application(), ViewModelControllerFactoryProvidingApplication {

    @ExperimentalTime
    override fun onCreate() {
        super.onCreate()

        HttpConfiguration.connectivityPublisher = AndroidConnectivityPublisher(this)
            .distinctUntilChanged()
        HttpConfiguration.httpRequestFactory = KtorHttpRequestFactory()
    }

    override val viewModelControllerFactory: ViewModelControllerFactory = SampleViewModelControllerFactoryImpl()
}
