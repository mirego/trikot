package com.mirego.trikot.bluetooth

import com.mirego.trikot.streams.cancellable.CancellableManager
import org.reactivestreams.Publisher

interface BluetoothScanResult {
    val name: String

    val physicalAddress: String

    val rssi: Publisher<Int>

    fun manufacturerSpecificData(manufacturerId: Int): Publisher<ByteArray>

    fun connect(cancellableManager: CancellableManager): BluetoothDevice
}
