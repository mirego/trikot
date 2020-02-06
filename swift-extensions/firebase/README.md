# Trikot.analytics swift extensions

## Installation
To use `Trikot.analytics/Firebase` with iOS, you must export `analytics` and `analytics-iosarm64` module in your exported framework. See [Trikot.patron build.gradle file](https://github.com/mirego/trikot.patron/blob/master/common/build.gradle) for a sample use case.

##### Setup Pod dependency
```groovy
  ENV['TRIKOT_FRAMEWORK_NAME']='ReplaceMeByTheFrameworkNameImportedByCocoaPods'
  pod 'Trikot.analytics/Firebase', :git => 'https://github.com/mirego/trikot.analytics.git'
```
Then, run `pod install`.

### Usage
Configure firebase like usual with ``


```swift
    import Trikot_analytics
    import Firebase

    // in application didFinishLaunchingWithOptions
    FirebaseApp.configure()
    AnalyticsConfiguration().analyticsManager = FirebaseAnalyticsService()
  ```
