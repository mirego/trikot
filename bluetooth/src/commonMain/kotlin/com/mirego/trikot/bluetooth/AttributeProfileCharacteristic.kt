package com.mirego.trikot.bluetooth

import com.mirego.trikot.streams.reactive.filter
import com.mirego.trikot.streams.reactive.map
import org.reactivestreams.Publisher

data class AttributeProfileCharacteristicEvent(val value: ByteArray?, val error: Throwable?)

interface AttributeProfileCharacteristic {
    val uuid: String
    val event: Publisher<AttributeProfileCharacteristicEvent>
    fun read()
    fun write(data: ByteArray)
    fun watch()
    fun watchWithIndication()
}

val AttributeProfileCharacteristic.value: Publisher<ByteArray> get() = event
    .filter { it.value != null }.map { it.value!! }

val AttributeProfileCharacteristic.error: Publisher<Throwable> get() = event
    .filter { it.error != null }.map { it.error!! }
