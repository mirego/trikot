package com.mirego.trikot.foundation.strings

import platform.Foundation.NSDiacriticInsensitiveSearch
import platform.Foundation.NSString
import platform.Foundation.create
import platform.Foundation.stringByFoldingWithOptions

actual fun String.normalize(): String =
    NSString.create(string = this)
        .stringByFoldingWithOptions(options = NSDiacriticInsensitiveSearch, locale = null)
