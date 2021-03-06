package com.mirego.trikot.bluetooth

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import org.reactivestreams.Publisher

class EmptyBluetoothManager : BluetoothManager {
    override val statePublisher = Publishers.behaviorSubject<BluetoothManager.State>()
    override val missingPermissionsPublisher = Publishers.behaviorSubject<List<BluetoothManager.Permission>>()

    override fun scanForDevices(
        cancellableManager: CancellableManager,
        serviceUUIDs: List<String>
    ): Publisher<List<BluetoothScanResult>> {
        return Publishers.behaviorSubject()
    }
}
