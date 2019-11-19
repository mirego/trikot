package com.mirego.trikot.metaviews

import android.R
import android.content.res.ColorStateList
import android.util.StateSet
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
    val supportedMode = ArrayList<IntArray>()
    val colors = ArrayList<Int>()

    highlighted?.let {
        colors.add(it.toIntColor())
        supportedMode.add(intArrayOf(R.attr.state_pressed))
    }
    selected?.let {
        colors.add(it.toIntColor())
        supportedMode.add(intArrayOf(R.attr.state_selected))
    }
    disabled?.let {
        colors.add(it.toIntColor())
        supportedMode.add(intArrayOf(-R.attr.state_enabled))
    }
    default?.let {
        colors.add(it.toIntColor())
        supportedMode.add(StateSet.WILD_CARD)
    }
    return ColorStateList(
        Array(colors.count()) { supportedMode[it] },
        colors.toIntArray()
    )
}

val MetaSelector<*>.hasAnyValue: Boolean get() = default != null || selected != null || disabled != null || highlighted != null
