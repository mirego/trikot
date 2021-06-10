package com.mirego.trikot.bluetooth.android

import android.bluetooth.le.ScanResult
import android.content.Context
import android.util.SparseArray
import com.mirego.trikot.bluetooth.BluetoothDevice
import com.mirego.trikot.bluetooth.BluetoothScanResult
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.distinctUntilChanged
import com.mirego.trikot.streams.reactive.map
import org.reactivestreams.Publisher
import java.util.Timer
import kotlin.concurrent.schedule
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.minutes

@ExperimentalTime
fun ScanResult.toBluetoothScanResult(context: Context): AndroidBluetoothScanResult {
    return AndroidBluetoothScanResult(this, context)
}

@ExperimentalTime
class AndroidBluetoothScanResult(
    private val scanResult: ScanResult,
    private val context: Context
) : BluetoothScanResult {
    private var timer: Timer? = null
    private val manufacturerDataPublisher = Publishers.behaviorSubject(SparseArray<ByteArray>())
    private val rssiPublisher = Publishers.behaviorSubject(scanResult.rssi)

    override val name: String = scanResult.device.name
    override val physicalAddress: String = scanResult.device.address
    override val rssi = rssiPublisher.distinctUntilChanged()

    var lostHeartbeatCallback: (() -> Unit)? = null

    init {
        doHeartbeat()
    }

    override fun connect(cancellableManager: CancellableManager): BluetoothDevice {
        return AndroidBluetoothDevice(scanResult.device, context, cancellableManager)
    }

    override fun manufacturerSpecificData(manufacturerId: Int): Publisher<ByteArray> {
        return manufacturerDataPublisher.map {
            it.get(manufacturerId)
        }.distinctUntilChanged()
    }

    override fun equals(other: Any?): Boolean {
        return (other as? AndroidBluetoothScanResult)?.let {
            other.name == name
        } ?: false
    }

    fun doHeartbeat() {
        timer?.cancel()
        updateManufacturerData()
        rssiPublisher.value = scanResult.rssi
        timer = Timer().also {
            it.schedule(Duration.minutes(1).toLong(DurationUnit.MILLISECONDS)) {
                lostHeartbeatCallback?.let {
                    it()
                }
            }
        }
    }

    private fun updateManufacturerData() {
        scanResult.scanRecord?.let {
            manufacturerDataPublisher.value = it.manufacturerSpecificData
        }
    }
}
