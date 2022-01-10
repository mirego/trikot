# Trikot.analytics swift extensions

## Installation
To use `Trikot.analytics/Mixpanel` with iOS, you must export `analytics` and `analytics-iosarm64` module in your exported framework. See [Trikot.patron build.gradle file](https://github.com/mirego/trikot.patron/blob/master/common/build.gradle) for a sample use case.

##### Setup Pod dependency
```groovy
  ENV['TRIKOT_FRAMEWORK_NAME']='ReplaceMeByTheFrameworkNameImportedByCocoaPods'
  pod 'Trikot.analytics/Mixpanel', :git => 'https://github.com/mirego/trikot.analytics.git'
```
Then, run `pod install`.

### Usage
Configure mixpanel like usual with ``


```swift
    import Trikot_analytics
    import Mixpanel

    // in application didFinishLaunchingWithOptions
    Mixpanel.initialize(token: YourMixpanelToken)
    AnalyticsConfiguration().analyticsManager = MixpanelAnalyticsService()
  ```
