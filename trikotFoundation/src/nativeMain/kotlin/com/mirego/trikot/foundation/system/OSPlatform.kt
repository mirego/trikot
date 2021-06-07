package com.mirego.trikot.foundation.system

enum class OSPlatform {
    iOS,
    tvOS,
    watchOS,
    macOS
}

expect var currentPlatform: OSPlatform
