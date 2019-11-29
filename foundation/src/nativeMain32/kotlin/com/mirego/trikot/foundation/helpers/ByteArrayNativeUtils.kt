package com.mirego.trikot.foundation.helpers

import kotlinx.cinterop.addressOf
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.create
import platform.posix.memcpy

object ByteArrayNativeUtils {
    @ExperimentalUnsignedTypes
    fun convert(data: NSData): ByteArray {
        val byteArray = ByteArray(data.length.toInt()).apply {
            usePinned {
                memcpy(it.addressOf(0), data.bytes, data.length)
            }
        }
        return byteArray
    }
    @ExperimentalUnsignedTypes
    fun convert(byteArray: ByteArray): NSData {
        return byteArray.usePinned {
            NSData.create(bytes = it.addressOf(0),
                length = byteArray.size.toUInt())
        }
    }
}
