package com.mirego.trikot.foundation.system

import kotlinx.cinterop.CValue
import kotlinx.cinterop.cValue
import platform.Foundation.NSOperatingSystemVersion
import platform.Foundation.NSProcessInfo

@Suppress("EnumEntryName")
enum class OSVersion(val platform: OSPlatform, val majorVersion: Int, val minorVersion: Int, val patchVersion: Int) {
    iOS_10(platform = OSPlatform.iOS, majorVersion = 10, minorVersion = 0, patchVersion = 0),
    iOS_11(platform = OSPlatform.iOS, majorVersion = 11, minorVersion = 0, patchVersion = 0),
    iOS_12(platform = OSPlatform.iOS, majorVersion = 12, minorVersion = 0, patchVersion = 0),
    iOS_13(platform = OSPlatform.iOS, majorVersion = 13, minorVersion = 0, patchVersion = 0),
    iOS_14(platform = OSPlatform.iOS, majorVersion = 14, minorVersion = 0, patchVersion = 0),
    tvOS_10(platform = OSPlatform.tvOS, majorVersion = 10, minorVersion = 0, patchVersion = 0),
    tvOS_11(platform = OSPlatform.tvOS, majorVersion = 11, minorVersion = 0, patchVersion = 0),
    tvOS_12(platform = OSPlatform.tvOS, majorVersion = 12, minorVersion = 0, patchVersion = 0),
    tvOS_13(platform = OSPlatform.tvOS, majorVersion = 13, minorVersion = 0, patchVersion = 0),
    tvOS_14(platform = OSPlatform.tvOS, majorVersion = 14, minorVersion = 0, patchVersion = 0),
    watchOS_3(platform = OSPlatform.watchOS, majorVersion = 3, minorVersion = 0, patchVersion = 0),
    watchOS_4(platform = OSPlatform.watchOS, majorVersion = 4, minorVersion = 0, patchVersion = 0),
    watchOS_5(platform = OSPlatform.watchOS, majorVersion = 5, minorVersion = 0, patchVersion = 0),
    watchOS_6(platform = OSPlatform.watchOS, majorVersion = 6, minorVersion = 0, patchVersion = 0),
    watchOS_7(platform = OSPlatform.watchOS, majorVersion = 7, minorVersion = 0, patchVersion = 0),
    macOS_10_12(platform = OSPlatform.macOS, majorVersion = 10, minorVersion = 12, patchVersion = 0),
    macOS_10_13(platform = OSPlatform.macOS, majorVersion = 10, minorVersion = 13, patchVersion = 0),
    macOS_10_14(platform = OSPlatform.macOS, majorVersion = 10, minorVersion = 14, patchVersion = 0),
    macOS_10_15(platform = OSPlatform.macOS, majorVersion = 10, minorVersion = 15, patchVersion = 0),
    macOS_11(platform = OSPlatform.macOS, majorVersion = 11, minorVersion = 0, patchVersion = 0);

    val operatingSystemVersion: CValue<NSOperatingSystemVersion>
        get() = cValue<NSOperatingSystemVersion> {
            majorVersion = majorVersion
            minorVersion = minorVersion
            patchVersion = patchVersion
        }
}

fun osVersionAtLeast(vararg versions: OSVersion): Boolean {
    return versions.filter { it.platform == CurrentPlatform.current }.any {
        NSProcessInfo.processInfo.isOperatingSystemAtLeastVersion(it.operatingSystemVersion)
    }
}
