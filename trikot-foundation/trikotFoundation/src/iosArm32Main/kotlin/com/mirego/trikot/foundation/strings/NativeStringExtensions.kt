package com.mirego.trikot.foundation.strings

import kotlinx.cinterop.BetaInteropApi
import platform.Foundation.NSDiacriticInsensitiveSearch
import platform.Foundation.NSLocale
import platform.Foundation.NSString
import platform.Foundation.create
import platform.Foundation.currentLocale
import platform.Foundation.stringByFoldingWithOptions

@OptIn(BetaInteropApi::class)
actual fun String.normalize(): String =
    NSString.create(string = this)
        .stringByFoldingWithOptions(options = NSDiacriticInsensitiveSearch, locale = NSLocale.currentLocale)
