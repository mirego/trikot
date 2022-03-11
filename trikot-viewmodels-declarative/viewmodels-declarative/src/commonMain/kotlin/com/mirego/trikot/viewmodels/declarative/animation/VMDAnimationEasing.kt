package com.mirego.trikot.viewmodels.declarative.animation

sealed interface VMDAnimationEasing {
    enum class Standard: VMDAnimationEasing {
        Linear, EaseIn, EaseOut, EaseInEaseOut
    }

    data class CubicBezier(
        val a: Float,
        val b: Float,
        val c: Float,
        val d: Float
    ): VMDAnimationEasing
}
