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
            AndroidKWord.setupFileSystem(FileSystem.SYSTEM, context)
            AndroidKWord.setupRemoteTranslationsSource("https://fa1b-70-28-93-175.ngrok-free.app/export", "915cd661-ef3a-4b89-ac60-fb82ef90a61a")
            AndroidKWord.setCurrentLanguageCode(this, "en")
        }
        val i18NFr = DefaultI18N(false).apply {
            AndroidKWord.setupFileSystem(FileSystem.SYSTEM, context)
            AndroidKWord.setupRemoteTranslationsSource("https://fa1b-70-28-93-175.ngrok-free.app/export", "915cd661-ef3a-4b89-ac60-fb82ef90a61a")
            AndroidKWord.setCurrentLanguageCode(this, "fr")
        }
        return FlowMultiLanguageI18N("en", mapOf("en" to i18NEn, "fr" to i18NFr))
    }

    companion object {
        lateinit var instance: TrikotApplication
            private set
    }
}
