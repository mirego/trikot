# Trikot.viewmodels swift extensions

- Easily bind ViewModels to UIKit components

## Installation
To use `Trikot.viewmodels` swift extensions, you must export `viewmodels` module in your exported framework. See [Trikot.patron build.gradle file](https://github.com/mirego/trikot.patron/blob/master/common/build.gradle.kts) for a sample use case.

##### Setup Pod dependency
```groovy
  ENV['TRIKOT_FRAMEWORK_NAME']='ReplaceMeByTheFrameworkNameImportedByCocoaPods'
  pod 'Trikot/viewmodels', :git => 'git@github.com:mirego/trikot.git'
```
Then, run `pod install`.
