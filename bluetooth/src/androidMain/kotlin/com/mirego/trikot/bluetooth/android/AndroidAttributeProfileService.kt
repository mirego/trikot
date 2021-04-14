package com.mirego.trikot.bluetooth.android

import android.bluetooth.BluetoothGattService
import com.mirego.trikot.bluetooth.AttributeProfileCharacteristic
import com.mirego.trikot.bluetooth.AttributeProfileService
import com.mirego.trikot.streams.reactive.Publishers
import org.reactivestreams.Publisher
import java.util.Locale

class AndroidAttributeProfileService(
    value: BluetoothGattService,
    bluetoothDevice: AndroidBluetoothDevice
) : AttributeProfileService {
    val androidCharacteristic = value.characteristics.fold(
        HashMap<String, AndroidAttributeProfileCharacteristic>()
            as Map<String, AndroidAttributeProfileCharacteristic>
    ) { result, value ->
        (
            mutableMapOf(
                value.uuid.toString()
                    .toUpperCase(Locale.ROOT) to
                    AndroidAttributeProfileCharacteristic(
                        value,
                        bluetoothDevice
                    )
            ) + result
            ).toMap()
    }

    @Suppress("UNCHECKED_CAST")
    override val characteristics = Publishers.behaviorSubject(
        androidCharacteristic
    ) as Publisher<Map<String, AttributeProfileCharacteristic>>
}
