package com.mirego.kword.sample

import android.app.Application
import com.mirego.trikot.kword.DefaultI18N
import com.mirego.trikot.kword.android.AndroidKWord
import com.mirego.trikot.kword.flow.FlowMultiLanguageI18N
import okio.FileSystem

class TrikotApplication : Application() {

    val serviceLocator: ServiceLocator by lazy { ServiceLocatorImpl(createI18NMap()) }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    private fun createI18NMap(): FlowMultiLanguageI18N {
        val context = this.baseContext
        val i18NEn = DefaultI18N(false).apply {
            AndroidKWord.setCurrentLanguageCode(this, FileSystem.SYSTEM, context, "en")
        }
        val i18NFr = DefaultI18N(false).apply {
            AndroidKWord.setCurrentLanguageCode(this, FileSystem.SYSTEM, context, "fr")
        }
        return FlowMultiLanguageI18N("en", mapOf("en" to i18NEn, "fr" to i18NFr))
    }

    companion object {
        lateinit var instance: TrikotApplication
            private set
    }
}
