package com.mirego.trikot.viewmodels.declarative.compose.extensions

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import com.mirego.trikot.viewmodels.declarative.components.animation.VMDAnimation
import com.mirego.trikot.viewmodels.declarative.components.animation.VMDAnimationEasing
import kotlin.time.DurationUnit

fun <T, V> VMDAnimatedPropertyChange<T, V>.animationSpec() : AnimationSpec<T> {
    return propertyChange.animation?.toComposeAnimationSpec() ?: snap()
}

fun <T> VMDAnimation.toComposeAnimationSpec() : AnimationSpec<T> {
    return when (this) {
        is VMDAnimation.Tween -> {
            tween(
                durationMillis = duration.toInt(DurationUnit.MILLISECONDS),
                delayMillis = delay.toInt(DurationUnit.MILLISECONDS),
                easing = easing.toComposeEasing()
            )
        }
        is VMDAnimation.Spring -> {
            spring(dampingRatio = dampingRatio, stiffness = stiffness)
        }
    }
}

fun VMDAnimationEasing.toComposeEasing() : Easing {
    return when (this) {
        VMDAnimationEasing.Standard.Linear -> LinearEasing
        VMDAnimationEasing.Standard.EaseIn -> LinearOutSlowInEasing
        VMDAnimationEasing.Standard.EaseOut -> FastOutLinearInEasing
        VMDAnimationEasing.Standard.EaseInEaseOut -> FastOutSlowInEasing
        is VMDAnimationEasing.CubicBezier -> {
            CubicBezierEasing(a = a, b = b, c = c, d = d)
        }
    }
}