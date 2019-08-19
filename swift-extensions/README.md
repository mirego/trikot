# Trikot.metaviews swift extensions

- Easily bind MetaViews to UIKit components

## Installation
To use `Trikot.metaviews` swift extensions, you must export `metaviews` and `metaviews-iosarm64` module in your exported framework. See [Trikot.patron build.gradle file](https://github.com/mirego/trikot.patron/blob/master/common/build.gradle) for a sample use case.

##### Setup Pod dependency
```groovy
  ENV['TRIKOT_FRAMEWORK_NAME']='ReplaceMeByTheFrameworkNameImportedByCocoaPods'
  pod 'Trikot.metaviews', :git => 'git@github.com:mirego/trikot.metaviews.git'
```
Then, run `pod install`.
