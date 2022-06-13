package com.mirego.trikot.viewmodels.declarative.animation

import kotlin.math.PI
import kotlin.math.sqrt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.DurationUnit

sealed interface VMDAnimation {
    data class Tween(
        val duration: Duration = 350.milliseconds,
        val delay: Duration = 0.milliseconds,
        val easing: VMDAnimationEasing = VMDAnimationEasing.Standard.EaseInEaseOut
    ) : VMDAnimation {
        val durationInSeconds: Double = duration.toDouble(DurationUnit.SECONDS)
        val delayInSeconds: Double = delay.toDouble(DurationUnit.SECONDS)
    }

    data class Spring(
        val dampingRatio: Float = DampingRatioNoBouncy,
        val stiffness: Float = StiffnessMedium
    ) : VMDAnimation {
        companion object {
            const val DampingRatioHighBouncy = 0.2f
            const val DampingRatioMediumBouncy = 0.5f
            const val DampingRatioLowBouncy = 0.75f
            const val DampingRatioNoBouncy = 1f

            const val StiffnessHigh = 10_000f
            const val StiffnessMedium = 1500f
            const val StiffnessMediumLow = 400f
            const val StiffnessLow = 200f
            const val StiffnessVeryLow = 50f
        }

        // Used in SwiftUI.
        // The amount of time to complete a single oscillation (period).
        // T = 2π√m/k
        val response = 2 * PI / sqrt(stiffness)
    }
}

fun withAnimation(animation: VMDAnimation, closure: () -> Unit) {
    VMDAnimationContext.animationStack.push(animation)
    closure()
    VMDAnimationContext.animationStack.pop()
}
