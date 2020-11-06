package com.trikot.sample

import com.mirego.trikot.foundation.concurrent.AtomicReference
import com.mirego.trikot.http.HttpConfiguration

object Environment {
    private val internalFlavor = AtomicReference(Environment.Flavor.RELEASE)

    var flavor: Flavor
        get() = internalFlavor.value
        set(value) {
            internalFlavor.setOrThrow(internalFlavor.value, value)
            HttpConfiguration.baseUrl = value.baseUrl
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
