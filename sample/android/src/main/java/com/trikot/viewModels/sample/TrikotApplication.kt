package com.trikot.viewmodels.sample

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.mirego.trikot.viewmodels.resources.ImageViewModelResourceManager
import com.mirego.trikot.viewmodels.resources.TextAppearanceSpanResourceManager
import com.trikot.sample.Environment
import com.trikot.viewmodels.sample.resource.SampleImageResourceProvider
import com.trikot.viewmodels.sample.resource.SampleTextAppearanceResourceProvider

class TrikotApplication : Application() {

    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences(
            packageName + "_preferences",
            Context.MODE_PRIVATE
        )
    }

    override fun onCreate() {
        super.onCreate()
        ImageViewModelResourceManager.provider = SampleImageResourceProvider()
        TextAppearanceSpanResourceManager.provider = SampleTextAppearanceResourceProvider()
        Environment.flavor = Environment.Flavor.valueOf(BuildConfig.BUILD_TYPE.toUpperCase())
    }
}
