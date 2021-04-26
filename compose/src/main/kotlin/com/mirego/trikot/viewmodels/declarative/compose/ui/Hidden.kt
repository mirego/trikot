package com.mirego.trikot.viewmodels.declarative.compose.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

fun Modifier.hidden(isHidden: Boolean): Modifier {
    if (isHidden) {
        return alpha(0f)
    }
    return this
}
