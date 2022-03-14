package com.mirego.trikot.kword.internal

import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithContentsOfFile
import platform.objc.object_getClass

internal actual object PlatformTranslationLoader {
    actual fun loadTranslations(path: String): Map<String, String>? {
        val cleanedPath = path.replace(".json", "")
        return  NSBundle.bundleForClass(object_getClass(0)!!).pathForResource(cleanedPath, "json")
            ?.let { NSString.stringWithContentsOfFile(it, NSUTF8StringEncoding, null) }
            ?.let { decodeTranslationFile(it) }
    }
}
