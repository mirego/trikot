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
        val memScopedData = memScoped { data }
        val byteArray = ByteArray(memScopedData.length.toInt()).apply {
            usePinned {
                memcpy(it.addressOf(0), memScopedData.bytes, memScopedData.length)
            }
        }
        return byteArray
    }

    @ExperimentalUnsignedTypes
    fun convert(byteArray: ByteArray): NSData {
        return memScoped {
            NSData.create(bytes = allocArrayOf(byteArray),
                length = byteArray.size.toUInt())
        }
    }
}
