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
    dependencies {
        maven { url("https://s3.amazonaws.com/mirego-maven/public") }
    }

    ios() {
        binaries {
            framework {
                export "com.mirego.trikot:bluetooth:$trikot_version"
            }
        }
    }
    sourceSets {
        commonMain {
            dependencies {
                 implementation "com.mirego.trikot:bluetooth:$trikot_version"
            }
        }
    }
```
