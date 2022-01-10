package com.trikot.sample

import com.mirego.trikot.foundation.concurrent.AtomicReference

object Environment {
    private val internalFlavor = AtomicReference(Environment.Flavor.RELEASE)

    var flavor: Flavor
        get() = internalFlavor.value
        set(value) {
            internalFlavor.setOrThrow(internalFlavor.value, value)
        }

    enum class Flavor(
        val baseUrl: String
    ) {
        DEBUG("https://..."),
        QA("https://..."),
        STAGING("https://..."),
        RELEASE("https://...")
    }
}
