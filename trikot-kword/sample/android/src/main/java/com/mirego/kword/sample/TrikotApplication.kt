package com.mirego.kword.sample

import android.app.Application
import com.mirego.trikot.kword.DefaultI18N
import com.mirego.trikot.kword.android.AndroidKWord
import com.mirego.trikot.kword.flow.FlowMultiLanguageI18N

class TrikotApplication : Application() {

    val serviceLocator: ServiceLocator by lazy { ServiceLocatorImpl(createI18NMap()) }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    private fun createI18NMap(): FlowMultiLanguageI18N {
        val i18NEn = DefaultI18N().apply {
            AndroidKWord.setCurrentLanguageCode(this, "en")
        }
        val i18NFr = DefaultI18N().apply {
            AndroidKWord.setCurrentLanguageCode(this, "fr")
        }
        return FlowMultiLanguageI18N("en", mapOf("en" to i18NEn, "fr" to i18NFr))
    }

    companion object {
        lateinit var instance: TrikotApplication
            private set
    }
}
