package com.mirego.trikot.bluetooth.android

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothProfile
import android.content.Context
import com.mirego.trikot.bluetooth.AttributeProfileCharacteristicEvent
import com.mirego.trikot.bluetooth.AttributeProfileService
import com.mirego.trikot.bluetooth.BluetoothCharacteristicException
import com.mirego.trikot.bluetooth.BluetoothConfiguration
import com.mirego.trikot.bluetooth.BluetoothDevice
import com.mirego.trikot.bluetooth.BluetoothManager
import com.mirego.trikot.foundation.concurrent.AtomicListReference
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.map
import com.mirego.trikot.streams.reactive.processors.combine
import java.util.Locale
import java.util.Timer
import kotlin.concurrent.schedule

class AndroidBluetoothDevice(
    private val device: android.bluetooth.BluetoothDevice,
    private val context: Context,
    private val cancellableManager: CancellableManager
) : BluetoothDevice {
    private val bluetoothCommandQueue = AtomicListReference<BluetoothCommand>()
    private var currentCommand: BluetoothCommand? = null
    private var commandTimer: Timer? = null

    private lateinit var gatt: BluetoothGatt

    override val name = device.name ?: ""
    override val physicalAddress = device.address ?: ""

    var androidProfiles: Map<String, AndroidAttributeProfileService>? = null
    override val attributeProfileServices =
        Publishers.behaviorSubject<Map<String, AttributeProfileService>>()
    private val connectionState = Publishers.behaviorSubject<Int>()

    override val isConnected =
        BluetoothConfiguration.bluetoothManager.statePublisher
            .combine(connectionState)
            .map { (bluetoothState, hasConnection) ->
                bluetoothState == BluetoothManager.State.ON &&
                    hasConnection == BluetoothProfile.STATE_CONNECTED
            }

    init {
        connect()
    }

    private fun connect() {
        gatt = device.connectGatt(
            context,
            false,
            object : BluetoothGattCallback() {

                override fun onConnectionStateChange(
                    gatt: BluetoothGatt?,
                    status: Int,
                    newState: Int
                ) {
                    super.onConnectionStateChange(gatt, status, newState)
                    if (status == 133) {
                        Thread.sleep(400)
                        println("retry bluetooth connection")
                        gatt?.close()
                        connect()
                        return
                    }

                    connectionState.value = newState

                    if (newState == BluetoothProfile.STATE_CONNECTED) {
                        gatt?.discoverServices()
                    }

                    if (status != 0) {
                        print("onConnectionStateChange failed with status $status")
                    }
                }

                override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
                    super.onServicesDiscovered(gatt, status)
                    gatt?.let {
                        androidProfiles = gatt.services.fold(
                            HashMap<String, AndroidAttributeProfileService>()
                                as Map<String, AndroidAttributeProfileService>
                        ) { result, value ->
                            (
                                mutableMapOf(
                                    value.uuid.toString().toUpperCase(Locale.ROOT) to
                                        AndroidAttributeProfileService(
                                            value,
                                            this@AndroidBluetoothDevice
                                        )
                                ) + result
                                ).toMap()
                        }
                        attributeProfileServices.value = androidProfiles
                    }
                    if (status != 0) {
                        print("onServicesDiscovered failed with status $status")
                    }
                }

                override fun onCharacteristicRead(
                    gatt: BluetoothGatt?,
                    characteristic: BluetoothGattCharacteristic?,
                    status: Int
                ) {
                    super.onCharacteristicRead(gatt, characteristic, status)
                    characteristic?.let {
                        updateValueForCharacteristic(characteristic)
                        completeCommand()

                        if (status != 0) {
                            updateErrorForCharacteristic(
                                characteristic,
                                BluetoothCharacteristicException(
                                    "onCharacteristicRead failed with status $status"
                                )
                            )
                        }
                    }
                }

                override fun onCharacteristicChanged(
                    gatt: BluetoothGatt?,
                    characteristic: BluetoothGattCharacteristic?
                ) {
                    super.onCharacteristicChanged(gatt, characteristic)

                    characteristic?.let {
                        updateValueForCharacteristic(characteristic)
                    }
                }

                private fun updateValueForCharacteristic(characteristic: BluetoothGattCharacteristic) {
                    updateEventForCharacteristic(characteristic, characteristic.value, null)
                }

                private fun updateErrorForCharacteristic(
                    characteristic: BluetoothGattCharacteristic,
                    error: Throwable
                ) {
                    println(
                        "onCharacteristicError (${characteristic.uuid}) " +
                            "${characteristic.value} -- ${error.localizedMessage}"
                    )
                    updateEventForCharacteristic(characteristic, null, error)
                }

                private fun updateEventForCharacteristic(
                    characteristic: BluetoothGattCharacteristic,
                    value: ByteArray?,
                    error: Throwable?
                ) {
                    val serviceId = characteristic.service.uuid.toString().toUpperCase(Locale.ROOT)
                    val characteristicId = characteristic.uuid.toString().toUpperCase(Locale.ROOT)
                    androidProfiles?.get(serviceId)
                        ?.androidCharacteristic?.get(characteristicId)?.event
                        ?.value = AttributeProfileCharacteristicEvent(value, error)
                }

                override fun onCharacteristicWrite(
                    gatt: BluetoothGatt?,
                    characteristic: BluetoothGattCharacteristic?,
                    status: Int
                ) {
                    super.onCharacteristicWrite(gatt, characteristic, status)
                    completeCommand()
                    characteristic?.let {
                        if (status != 0) {
                            updateErrorForCharacteristic(
                                characteristic,
                                BluetoothCharacteristicException(
                                    "onCharacteristicWrite failed with status $status"
                                )
                            )
                        }
                    }
                }

                override fun onDescriptorWrite(
                    gatt: BluetoothGatt?,
                    descriptor: BluetoothGattDescriptor?,
                    status: Int
                ) {
                    super.onDescriptorWrite(gatt, descriptor, status)
                    completeCommand()

                    if (status != 0) {
                        println("onDescriptorWrite failed with status $status")
                    }
                }

                override fun onDescriptorRead(
                    gatt: BluetoothGatt?,
                    descriptor: BluetoothGattDescriptor?,
                    status: Int
                ) {
                    super.onDescriptorRead(gatt, descriptor, status)
                    completeCommand()

                    if (status != 0) {
                        println("onDescriptorRead failed with status $status")
                    }
                }

                override fun onReliableWriteCompleted(gatt: BluetoothGatt?, status: Int) {
                    super.onReliableWriteCompleted(gatt, status)
                    completeCommand()

                    if (status != 0) {
                        println("onReliableWriteCompleted failed with status $status")
                    }
                }
            }
        )
        cancellableManager.add {
            gatt.disconnect()
            gatt.close()
        }
    }

    // Make sure we execute our commands one after the other
    private fun addCommand(command: () -> Unit) {
        bluetoothCommandQueue.add(BluetoothCommand(command))
        executeNextCommandIfNeeded()
    }

    private fun executeNextCommandIfNeeded() {
        if (currentCommand == null) {
            currentCommand = bluetoothCommandQueue.value.firstOrNull()
            currentCommand?.let { command ->
                commandTimer?.cancel()

                val buffer: Long = 100
                val timeout: Long = 200
                Timer(false).also { executionDelay ->
                    executionDelay.schedule(buffer) {
                        command.execute()
                        commandTimer = Timer(false).also { timer ->
                            timer.schedule(timeout) {
                                completeCommand()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun completeCommand() {
        commandTimer?.cancel()
        currentCommand?.let { bluetoothCommandQueue.remove(it) }
        currentCommand = null
        executeNextCommandIfNeeded()
    }

    fun readCharacteristic(bluetoothGattCharacteristic: BluetoothGattCharacteristic) {
        addCommand {
            if (!gatt.readCharacteristic(bluetoothGattCharacteristic)) {
                println(
                    "Unable to readCharacteristic for " +
                        "$bluetoothGattCharacteristic " +
                        "(${bluetoothGattCharacteristic.uuid})"
                )
            }
        }
    }

    fun writeCharacteristic(bluetoothGattCharacteristic: BluetoothGattCharacteristic) {
        addCommand {
            if (!gatt.writeCharacteristic(bluetoothGattCharacteristic)) {
                println(
                    "Unable to writeCharacteristic for " +
                        "$bluetoothGattCharacteristic " +
                        "(${bluetoothGattCharacteristic.uuid})"
                )
            }
        }
    }

    fun setCharacteristicNotification(
        bluetoothGattCharacteristic: BluetoothGattCharacteristic,
        enable: Boolean
    ) {
        addCommand {
            if (!gatt.setCharacteristicNotification(bluetoothGattCharacteristic, enable)) {
                println(
                    "Unable to setCharacteristicNotification for " +
                        "$bluetoothGattCharacteristic " +
                        "(${bluetoothGattCharacteristic.uuid})"
                )
            }
        }
    }

    fun readDescriptor(descriptor: BluetoothGattDescriptor?) {
        addCommand {
            if (!gatt.readDescriptor(descriptor)) {
                println("Unable to readDescriptor for $descriptor (${descriptor?.uuid})")
            }
        }
    }

    fun writeDescriptor(descriptor: BluetoothGattDescriptor?) {
        addCommand {
            if (!gatt.writeDescriptor(descriptor)) {
                println("Unable to writeDescriptor for $descriptor (${descriptor?.uuid})")
            }
        }
    }

    class BluetoothCommand(private val command: () -> Unit) {
        fun execute() {
            command()
        }
    }
}
