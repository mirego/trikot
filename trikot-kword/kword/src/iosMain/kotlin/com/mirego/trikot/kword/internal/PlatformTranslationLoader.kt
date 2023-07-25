package com.mirego.trikot.kword.internal

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithContentsOfFile
import platform.objc.object_getClass

internal actual object PlatformTranslationLoader {
    @OptIn(BetaInteropApi::class)
    private val frameworkBundle: NSBundle
        get() = NSBundle.bundleForClass(object_getClass(0)!!)

    actual fun loadTranslations(path: String): Map<String, String>? {
        val cleanedPath = path.replace(".json", "")
        return loadTranslationsFromBundle(cleanedPath, frameworkBundle) ?: loadTranslationsFromBundle(cleanedPath, NSBundle.mainBundle)
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun loadTranslationsFromBundle(path: String, bundle: NSBundle): Map<String, String>? =
        bundle.pathForResource(path, "json")
            ?.let { NSString.stringWithContentsOfFile(it, NSUTF8StringEncoding, null) }
            ?.let { decodeTranslationFile(it) }
}
