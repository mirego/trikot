# Trikot.bluetooth

Elegant implementation of Multiplatform Bluetooth in ReactiveStreams.

#### Usage
Discover devices
```kotlin
    BluetoothConfiguration.bluetoothManager.scanForDevice(cancellableManager, listOf("UUIDS")).subscribe(cancellableManager) {
        // List of BluetoothScanResult
    }
```

Connect to device
```kotlin
val bluetoothScanResult = ...
val device = bluetoothScanResult.connect(cancellableManager)
device.isConnected.subscribe(cancellableManager) {
    // True when connected
}
```

Retrieve AttributeProfileServices
```kotlin
val services = device.attributeProfileServices.subscribe(cancellableManager) {
    // Map of UUIDs - AttributeProfileService
}
```

Retrieve AttributeProfileCharacteristics
```kotlin
val attributeProfileService = ... attributeProfileService.characteristics.subscribe(cancellableManager) {
    // Map of UUIDs - AttributeProfileCharacteristic
}
```

Receive event (value or error)
```kotlin
val attributeProfileCharacteristic = ... attributeProfileCharacteristic.event.subscribe(cancellableManager) {
    // AttributeProfileCharacteristicEvent
}
```

Read value
```kotlin
attributeProfileCharacteristic.read()
```

Write value
```kotlin
val byteArray = ...
attributeProfileCharacteristic.write(byteArray)
```

Watch value (subscribe for value change). Must call the right method depending on the characteristic type.
```kotlin
attributeProfileCharacteristic.watch()
// OR
attributeProfileCharacteristic.watchWithIndication()
```


#### Swift
See [swift extensions](./swift-extensions/README.md) for more information.

#### Android
```kotlin
    val context = this // application context
    BluetoothConfiguration.bluetoothManager = AndroidBluetoothManager(context)
```

## Common
##### Import dependencies
```groovy
    maven { url('https://s3.amazonaws.com/mirego-maven/public') }

    api "com.mirego.trikot:bluetooth:$trikot_bluetooth_version"
    jvm "com.mirego.trikot:bluetooth-jvm:$trikot_bluetooth_version"
    js "com.mirego.trikot:bluetooth-js:$trikot_bluetooth_version"
    iosx64 "com.mirego.trikot:bluetooth-iosx64:$trikot_bluetooth_version"
    iosarm64 "com.mirego.trikot:bluetooth-iosarm64:$trikot_bluetooth_version"
```

## License

Trikot.bluetooth is © 2018-2019 [Mirego](https://www.mirego.com) and may be freely distributed under the [New BSD license](http://opensource.org/licenses/BSD-3-Clause). See the [`LICENSE.md`](https://github.com/mirego/trikot.bluetooth/blob/master/LICENSE.md) file.

## About Mirego

[Mirego](https://www.mirego.com) is a team of passionate people who believe that work is a place where you can innovate and have fun. We’re a team of [talented people](https://life.mirego.com) who imagine and build beautiful Web and mobile applications. We come together to share ideas and [change the world](http://www.mirego.org).

We also [love open-source software](https://open.mirego.com) and we try to give back to the community as much as we can.
