package com.mirego.kword.sample

import android.app.Application
import com.mirego.trikot.kword.DefaultI18N
import com.mirego.trikot.kword.android.AndroidKWord
import com.mirego.trikot.kword.flow.FlowMultiLanguageI18N
import com.mirego.trikot.kword.remote.update.KwordRemoteUpdate
import okio.FileSystem

class TrikotApplication : Application() {
    val serviceLocator: ServiceLocator by lazy { ServiceLocatorImpl(createI18NMap()) }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    private fun createI18NMap(): FlowMultiLanguageI18N {
        val translationsUrl = "https://airtransattest.blob.core.windows.net/air-transat/translations"
        val translationsVersion = "v1.23"
        KwordRemoteUpdate.setupFileSystem(FileSystem.SYSTEM, this)
        KwordRemoteUpdate.setupRemoteTranslationsSource(translationsUrl, translationsVersion)
        val i18NEn = DefaultI18N().apply {
            AndroidKWord.setCurrentLanguageCode(this, "en")
            KwordRemoteUpdate.setCurrentLanguageCodes(this, "en")
        }
        val i18NFr = DefaultI18N().apply {
            AndroidKWord.setCurrentLanguageCode(this, "fr")
            KwordRemoteUpdate.setCurrentLanguageCodes(this, "fr")
        }
        return FlowMultiLanguageI18N("en", mapOf("en" to i18NEn, "fr" to i18NFr))
    }

    companion object {
        lateinit var instance: TrikotApplication
            private set
    }
}
