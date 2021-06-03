package com.mirego.sample

import android.app.Application
import com.mirego.trikot.http.HttpConfiguration
import com.mirego.trikot.http.android.AndroidConnectivityPublisher
import com.mirego.trikot.http.android.requestFactory.KtorHttpRequestFactory
import com.mirego.trikot.streams.reactive.distinctUntilChanged
import java.util.Locale
import kotlin.time.ExperimentalTime

class TrikotApplication : Application() {

    @ExperimentalTime
    override fun onCreate() {
        super.onCreate()
        Environment.flavor = Environment.Flavor.valueOf(BuildConfig.BUILD_TYPE.uppercase(Locale.ROOT))

        HttpConfiguration.connectivityPublisher = AndroidConnectivityPublisher(this)
            .distinctUntilChanged()
        HttpConfiguration.httpRequestFactory = KtorHttpRequestFactory()
    }
}
