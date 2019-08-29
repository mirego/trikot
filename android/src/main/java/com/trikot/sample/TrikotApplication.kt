package com.trikot.sample

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.mirego.trikot.http.HttpConfiguration
import com.mirego.trikot.http.android.ktx.AndroidConnectivityPublisher
import com.mirego.trikot.http.android.ktx.requestFactory.KtorHttpRequestFactory
import com.mirego.trikot.kword.android.ktx.AndroidKWord
import com.mirego.trikot.metaviews.resources.MetaImageResourceManager
import com.mirego.trikot.streams.reactive.distinctUntilChanged
import com.trikot.sample.resource.SampleImageResourceProvider
import kotlinx.serialization.ImplicitReflectionSerializer

class TrikotApplication : Application() {

    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences(
            packageName + "_preferences", Context.MODE_PRIVATE
        )
    }

    @ImplicitReflectionSerializer
    override fun onCreate() {
        super.onCreate()
        AndroidKWord.setCurrentLanguageCode("en")
        MetaImageResourceManager.provider = SampleImageResourceProvider()
        Environment.flavor = Environment.Flavor.valueOf(BuildConfig.BUILD_TYPE.toUpperCase())

        HttpConfiguration.connectivityPublisher = AndroidConnectivityPublisher(this)
            .distinctUntilChanged()
        HttpConfiguration.httpRequestFactory = KtorHttpRequestFactory()
    }
}
