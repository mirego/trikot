package com.trikot.sample

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.facebook.FacebookSdk
import com.jakewharton.threetenabp.AndroidThreeTen
import com.mirego.skratch.kword.AndroidKWord
import com.mirego.trikot.http.HttpConfiguration
import com.mirego.trikot.metaviews.resources.MetaImageResourceManager
import com.mirego.trikot.streams.reactive.distinctUntilChanged
import com.tv5.android.auth.FirebaseAuthTokenProvider
import com.tv5.android.bindings.TV5ImageProvider
import com.tv5.android.user.FirebaseUserInfoDataSource
import com.tv5.mobile.AppConfiguration
import com.trikot.sample.Environment
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
        AndroidKWord.init()
        MetaImageResourceManager.provider = TV5ImageProvider()
        Environment.flavor = Environment.Flavor.valueOf(BuildConfig.BUILD_TYPE.toUpperCase())
        AndroidThreeTen.init(this)
        HttpConfiguration.connectivityPublisher =
            AndroidConnectivityPublisher(this)
                .distinctUntilChanged()
        FacebookSdk.setApplicationId(Environment.flavor.facebookAppId)
        FacebookSdk.sdkInitialize(this)

        AppConfiguration.authTokenProvider = FirebaseAuthTokenProvider(sharedPreferences)
        AppConfiguration.userInfoDataSource = FirebaseUserInfoDataSource()
    }
}
