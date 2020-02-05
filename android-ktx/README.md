# Trikot.bluetooth Android Kotlin extensions

### Installation
Add dependency
```groovy
dependencies {
    implementation "com.mirego.trikot.bluetooth:android-ktx:$trikot_bluetooth_android_ktx_version"
}
```

### Configure
```kotlin
    val context = this // application context
    BluetoothConfiguration.bluetoothManager = AndroidBluetoothManager(context)
```
