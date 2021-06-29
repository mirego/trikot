package com.mirego.sample

import android.app.Application
import com.mirego.trikot.http.HttpConfiguration
import com.mirego.trikot.http.android.AndroidConnectivityPublisher
import com.mirego.trikot.http.android.requestFactory.KtorHttpRequestFactory
import com.mirego.trikot.streams.reactive.distinctUntilChanged
import kotlin.time.ExperimentalTime

class TrikotApplication : Application() {

    @ExperimentalTime
    override fun onCreate() {
        super.onCreate()

        HttpConfiguration.connectivityPublisher = AndroidConnectivityPublisher(this)
            .distinctUntilChanged()
        HttpConfiguration.httpRequestFactory = KtorHttpRequestFactory()
    }
}
