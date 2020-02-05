package com.mirego.trikot.bluetooth

import com.mirego.trikot.streams.cancellable.CancellableManager
import org.reactivestreams.Publisher

interface BluetoothManager {
    val statePublisher: Publisher<State>
    val hasPermissionPublisher: Publisher<Boolean>

    fun scanForDevices(cancellableManager: CancellableManager, serviceUUIDs: List<String>): Publisher<List<BluetoothScanResult>>

    enum class State {
        ON,
        OFF
    }
}
