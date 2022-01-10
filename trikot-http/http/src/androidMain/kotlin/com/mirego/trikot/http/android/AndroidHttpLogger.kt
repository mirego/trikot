package com.mirego.trikot.http.android

import android.util.Log
import io.ktor.client.features.logging.Logger

/**
 * Use this Logger when instantiating the KtorHttpRequestFactory.
 *
 * ex.:
 * val factory = KtorHttpRequestFactory(httpLogLevel = LogLevel.INFO, httpLogger = AndroidHttpLogger())
 */
class AndroidHttpLogger : Logger {
    override fun log(message: String) {
        Log.d("HTTP", message)
    }
}
