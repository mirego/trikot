package com.mirego.trikot.viewmodels.declarative.compose.extensions

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.unit.Density

fun Modifier.hidden(isHidden: Boolean): Modifier {
    var effectiveAlpha: Float? = if (isHidden) 0f else null
    effectiveAlpha = foldIn(effectiveAlpha) { value, element ->
        (element as? AlphaOverride)?.let {
            return@foldIn it.alpha
        }
        return@foldIn value
    }

    return if (effectiveAlpha != null) {
        alpha(effectiveAlpha)
    } else {
        this
    }
}

fun Modifier.alphaOverride(alpha: Float): Modifier = then(AlphaOverride(alpha))

fun Modifier.isOverridingAlpha(): Boolean = any { it is AlphaOverride }

private class AlphaOverride(val alpha: Float) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?) = this@AlphaOverride

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherModifier = other as? AlphaOverride ?: return false
        return alpha == otherModifier.alpha
    }

    override fun hashCode(): Int {
        return alpha.hashCode()
    }
}
