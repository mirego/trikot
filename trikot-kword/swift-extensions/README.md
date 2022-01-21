## Installation

To use `Trikot.kword` swift extensions, you must export `kword` module in your exported framework. See [Trikot.patron build.gradle file](https://github.com/mirego/trikot.patron/blob/master/common/build.gradle.kts) for a sample use case.

##### Setup Pod dependency

```groovy
  ENV['TRIKOT_FRAMEWORK_NAME']='ReplaceMeByTheFrameworkNameImportedByCocoaPods'
  pod 'Trikot/kword', :git => 'git@github.com:mirego/trikot.git', :tag => properties['trikot_version']
```

Then, run `pod install`.

### Change current language

```swift
    import Trikot
    ...
    TrikotKWord.shared.setCurrentLanguage("en")
```
