package com.mirego.trikot.bluetooth.android.ktx

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.STATE_ON
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.ParcelUuid
import androidx.core.content.ContextCompat
import com.mirego.trikot.bluetooth.BluetoothManager
import com.mirego.trikot.bluetooth.BluetoothScanResult
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import mirego.trikot.bluetooth.AndroidBluetoothScanResult
import mirego.trikot.bluetooth.toBluetoothScanResult
import org.reactivestreams.Publisher
import java.util.Timer
import java.util.UUID
import kotlin.concurrent.schedule

class AndroidBluetoothManager(val context: Context) : BluetoothManager {
    private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    override val statePublisher = Publishers.behaviorSubject<BluetoothManager.State>()
    override val missingPermissionsPublisher = Publishers.behaviorSubject<List<BluetoothManager.Permission>>()

    fun refreshLocationPermission() {
        missingPermissionsPublisher.value = if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED) {
            emptyList()
        } else {
            listOf(BluetoothManager.Permission.LOCATION)
        }
    }

    init {
        refreshLocationPermission()
        if (bluetoothAdapter != null) {
            statePublisher.value =
                if (bluetoothAdapter.enable()) {
                    BluetoothManager.State.ON
                } else {
                    BluetoothManager.State.OFF
                }
        }

        val filter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        context.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val action = intent.action
                if (action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                    val state = intent.getIntExtra(
                        BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR
                    )
                    when (state) {
                        BluetoothAdapter.STATE_OFF ->
                            statePublisher.value = BluetoothManager.State.OFF
                        STATE_ON ->
                            statePublisher.value = BluetoothManager.State.ON
                    }
                }
            }
        }, filter)
    }

    override fun scanForDevices(
        cancellableManager: CancellableManager,
        serviceUUIDs: List<String>
    ): Publisher<List<BluetoothScanResult>> {
        val devicesPublisher = Publishers.behaviorSubject<List<BluetoothScanResult>>(emptyList())

        val callback = object : ScanCallback() {
            val foundDevice = HashMap<String, BluetoothScanResult>()

            override fun onBatchScanResults(results: MutableList<ScanResult>?) {
                super.onBatchScanResults(results)
                results?.let {
                    results.forEach {
                        addResultIfNecessary(it)
                    }
                }
            }

            override fun onScanFailed(errorCode: Int) {
                super.onScanFailed(errorCode)
                devicesPublisher.error = Exception("$errorCode")
            }

            override fun onScanResult(callbackType: Int, result: ScanResult?) {
                super.onScanResult(callbackType, result)
                result?.let {
                    if (callbackType == ScanSettings.CALLBACK_TYPE_MATCH_LOST) {
                        removeResult(result)
                    } else {
                        addResultIfNecessary(result)
                    }
                }
            }

            private fun addResultIfNecessary(result: ScanResult) {
                if (foundDevice[result.device.address] == null) {
                    foundDevice[result.device.address] = result.toBluetoothScanResult(context)
                        .also {
                            it.lostHeartbeatCallback = {
                                foundDevice.remove(result.device.address)
                                devicesPublisher.value = foundDevice.values.toList()
                            }
                        }
                    devicesPublisher.value = foundDevice.values.toList()
                } else {
                    (foundDevice[result.device.address] as AndroidBluetoothScanResult).doHeartbeat()
                }
            }

            private fun removeResult(result: ScanResult) {
                foundDevice.remove(result.device.address)
                devicesPublisher.value = foundDevice.values.toList()
            }
        }

        startScanWhenReady(serviceUUIDs, callback, cancellableManager)
        return devicesPublisher
    }

    private fun startScanWhenReady(
        serviceUUIDs: List<String>,
        callback: ScanCallback,
        cancellableManager: CancellableManager
    ) {
        val filters = serviceUUIDs.map {
            ScanFilter.Builder().setServiceUuid(ParcelUuid(UUID.fromString(it))).build()
        }

        val scanSettings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .build()

        val bluetoothScanner = bluetoothAdapter.bluetoothLeScanner
        if (bluetoothScanner != null) {
            bluetoothScanner.startScan(filters.subList(0, 1), scanSettings, callback)

            cancellableManager.add {
                if (bluetoothAdapter.state == STATE_ON) {
                    bluetoothScanner.stopScan(callback)
                }
            }
        } else {
            Timer().also {
                it.schedule(200) {
                    startScanWhenReady(serviceUUIDs, callback, cancellableManager)
                }
                cancellableManager.add { it.cancel() }
            }
        }
    }
}
