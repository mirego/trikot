package com.mirego.trikot.bluetooth

import org.reactivestreams.Publisher

interface BluetoothDevice {
    val name: String

    val physicalAddress: String

    val attributeProfileServices: Publisher<Map<String, AttributeProfileService>>

    val isConnected: Publisher<Boolean>
}
