package com.mirego.trikot.viewmodels

import android.graphics.drawable.Drawable
import android.widget.TextView

var TextView.drawableStart: Drawable?
    get() = compoundDrawables[0]
    set(value) = setDrawables(value, drawableTop, drawableEnd, drawableBottom)

var TextView.drawableTop: Drawable?
    get() = compoundDrawables[1]
    set(value) = setDrawables(drawableStart, value, drawableEnd, drawableBottom)

var TextView.drawableEnd: Drawable?
    get() = compoundDrawables[2]
    set(value) = setDrawables(drawableStart, drawableTop, value, drawableBottom)

var TextView.drawableBottom: Drawable?
    get() = compoundDrawables[3]
    set(value) = setDrawables(drawableStart, drawableTop, drawableEnd, value)

private fun TextView.setDrawables(
    start: Drawable?,
    top: Drawable?,
    end: Drawable?,
    bottom: Drawable?
) {
    setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom)
}
