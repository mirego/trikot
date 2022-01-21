# Trikot.bluetooth swift extensions

## Installation
To use `Trikot.bluetooth` with iOS, you must export `bluetooth` module in your exported framework. See [Trikot.patron build.gradle file](https://github.com/mirego/trikot.patron/blob/master/common/build.gradle.kts) for a sample use case.

##### Setup Pod dependency
```groovy
  ENV['TRIKOT_FRAMEWORK_NAME']='ReplaceMeByTheFrameworkNameImportedByCocoaPods'
  pod 'Trikot/bluetooth', :git => 'https://github.com/mirego/trikot.git', :tag => properties['trikot_version']
```
Then, run `pod install`.

### Usage
```swift
    import Trikot

    // in application didFinishLaunchingWithOptions
    BluetoothConfiguration().bluetoothManager = TrikotBluetoothManager()
```
