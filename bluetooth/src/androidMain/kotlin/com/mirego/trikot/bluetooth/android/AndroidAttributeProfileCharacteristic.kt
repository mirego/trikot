package com.mirego.trikot.bluetooth.android

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import com.mirego.trikot.bluetooth.AttributeProfileCharacteristic
import com.mirego.trikot.bluetooth.AttributeProfileCharacteristicEvent
import com.mirego.trikot.streams.reactive.Publishers
import java.util.Locale
import java.util.UUID

class AndroidAttributeProfileCharacteristic(
    private val bluetoothGattCharacteristic: BluetoothGattCharacteristic,
    private val bluetoothDevice: AndroidBluetoothDevice
) : AttributeProfileCharacteristic {
    override val event = Publishers.publishSubject<AttributeProfileCharacteristicEvent>()

    override val uuid: String = bluetoothGattCharacteristic.uuid.toString().uppercase(Locale.ROOT)

    override fun read() {
        bluetoothDevice.readCharacteristic(bluetoothGattCharacteristic)
    }

    override fun write(data: ByteArray) {
        bluetoothGattCharacteristic.value = data
        bluetoothDevice.writeCharacteristic(bluetoothGattCharacteristic)
    }

    override fun watchWithIndication() {
        bluetoothDevice.setCharacteristicNotification(bluetoothGattCharacteristic, true)

        val descriptor = bluetoothGattCharacteristic
            .getDescriptor(CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR)
        descriptor.value = BluetoothGattDescriptor.ENABLE_INDICATION_VALUE
        bluetoothDevice.writeDescriptor(descriptor)
    }

    override fun watch() {
        bluetoothDevice.setCharacteristicNotification(bluetoothGattCharacteristic, true)

        val descriptor = bluetoothGattCharacteristic
            .getDescriptor(CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR)
        descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
        bluetoothDevice.writeDescriptor(descriptor)
    }

    companion object {
        val CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR =
            UUID.fromString("00002902-0000-1000-8000-00805F9B34FB")
    }
}
