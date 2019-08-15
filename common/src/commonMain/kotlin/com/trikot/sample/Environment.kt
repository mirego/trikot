package com.trikot.sample

import com.mirego.trikot.http.HttpConfiguration
import com.mirego.trikot.streams.concurrent.AtomicReference

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
        DEBUG("https://tv5unis-api-ci.dev.mirego.com"),
        QA("https://tv5unis-api-qa.dev.mirego.com"),
        STAGING("https://tv5unis-api-staging.dev.mirego.com"),
        RELEASE("https://api.tv5.ca")
    }
}
