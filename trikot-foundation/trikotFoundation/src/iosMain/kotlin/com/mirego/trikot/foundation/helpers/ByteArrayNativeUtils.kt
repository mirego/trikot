package com.mirego.trikot.foundation.helpers

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.UnsafeNumber
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.create
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class, UnsafeNumber::class, BetaInteropApi::class)
object ByteArrayNativeUtils {
    fun convert(data: NSData): ByteArray {
        return data.bytes?.let { bytes ->
            ByteArray(data.length.toInt()).apply {
                usePinned { pinned ->
                    memcpy(pinned.addressOf(0), bytes, data.length)
                }
            }
        } ?: ByteArray(0)
    }

    fun convert(byteArray: ByteArray): NSData {
        return byteArray.usePinned {
            NSData.create(
                bytes = it.addressOf(0),
                length = byteArray.size.convert()
            )
        }
    }
}
