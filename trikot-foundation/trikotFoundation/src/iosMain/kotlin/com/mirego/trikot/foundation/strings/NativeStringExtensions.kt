package com.mirego.trikot.foundation.strings

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.UnsafeNumber
import platform.Foundation.NSDiacriticInsensitiveSearch
import platform.Foundation.NSLocale
import platform.Foundation.NSString
import platform.Foundation.create
import platform.Foundation.currentLocale
import platform.Foundation.stringByFoldingWithOptions

@OptIn(BetaInteropApi::class, UnsafeNumber::class)
actual fun String.normalize(): String {
    return NSString.create(string = this)
        .stringByFoldingWithOptions(options = NSDiacriticInsensitiveSearch, locale = NSLocale.currentLocale)
}
