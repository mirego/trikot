package com.mirego.trikot.metaviews

import com.mirego.trikot.metaviews.properties.Color

fun Color.toIntColor(): Int {
    try {
        return android.graphics.Color.parseColor(hexARGB("#"))
    } catch (e: NumberFormatException) {
    }
    return 0
}
