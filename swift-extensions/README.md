## Installation
To use `Trikot.kword` swift extensions, you must export `kword` and `kword-iosarm64` module in your exported framework. See [Trikot.patron build.gradle file](https://github.com/mirego/trikot.patron/blob/master/common/build.gradle) for a sample use case.

##### Setup Pod dependency
```groovy
  ENV['TRIKOT_FRAMEWORK_NAME']='ReplaceMeByTheFrameworkNameImportedByCocoaPods'
  pod 'Trikot.kword', :git => 'git@github.com:mirego/trikot.kword.git'
```
Then, run `pod install`.

### Change current language
```swift
    import Trikot_kword
    ...
    TrikotKWord.shared.setCurrentLanguage("en")
```
