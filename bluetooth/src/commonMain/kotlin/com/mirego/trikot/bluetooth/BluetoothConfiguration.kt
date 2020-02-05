package com.mirego.trikot.bluetooth

import com.mirego.trikot.foundation.concurrent.AtomicReference

object BluetoothConfiguration {
    private val internalBluetoothService = AtomicReference<BluetoothManager>(EmptyBluetoothManager())

    var bluetoothManager: BluetoothManager
        get() = internalBluetoothService.value
        set(value) { internalBluetoothService.compareAndSet(internalBluetoothService.value, value) }
}
