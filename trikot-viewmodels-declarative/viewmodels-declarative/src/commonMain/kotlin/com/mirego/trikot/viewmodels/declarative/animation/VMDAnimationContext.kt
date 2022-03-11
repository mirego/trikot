package com.mirego.trikot.viewmodels.declarative.animation

import com.mirego.trikot.foundation.concurrent.AtomicStackReference

class VMDAnimationContext {
    companion object {
        val animationStack = AtomicStackReference<VMDAnimation>()
    }
}