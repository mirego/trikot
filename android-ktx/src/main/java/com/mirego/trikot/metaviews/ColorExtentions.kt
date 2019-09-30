package com.mirego.trikot.metaviews

import android.R
import android.content.res.ColorStateList
import com.mirego.trikot.metaviews.properties.Color
import com.mirego.trikot.metaviews.properties.MetaSelector

fun Color.toIntColor(): Int {
    try {
        return android.graphics.Color.parseColor(hexARGB("#"))
    } catch (e: NumberFormatException) {
    }
    return 0
}

fun MetaSelector<Color>.toColorStateList(): ColorStateList {
    val supportedMode = ArrayList<Int>()
    val colors = ArrayList<IntArray>()

    default?.let {
        colors.add(intArrayOf(it.toIntColor()))
        supportedMode.add(R.attr.state_enabled)
    }
    highlighted?.let {
        colors.add(intArrayOf(it.toIntColor()))
        supportedMode.add(R.attr.state_pressed)
    }
    selected?.let {
        colors.add(intArrayOf(it.toIntColor()))
        supportedMode.add(R.attr.state_selected)
    }
    disabled?.let {
        colors.add(intArrayOf(it.toIntColor()))
        supportedMode.add(-R.attr.state_enabled)
    }
    return ColorStateList(
        Array(colors.count()) { colors[it] },
        supportedMode.toIntArray()
    )
}

val MetaSelector<Color>.hasAnyValue: Boolean get() = default != null || selected != null || disabled != null || highlighted != null
