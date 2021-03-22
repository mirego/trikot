package com.mirego.trikot.foundation.helpers

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.get
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.create

object ByteArrayNativeUtils {
    @ExperimentalUnsignedTypes
    fun convert(data: NSData): ByteArray {
        return data.bytes?.let {
            val dataPointer: CPointer<ByteVar> = it.reinterpret()
            ByteArray(data.length.toInt()) { index -> dataPointer[index] }
        } ?: ByteArray(0)
    }
    @ExperimentalUnsignedTypes
    fun convert(byteArray: ByteArray): NSData {
        return byteArray.usePinned {
            NSData.create(
                bytes = it.addressOf(0),
                length = byteArray.size.toUInt()
            )
        }
    }
}
