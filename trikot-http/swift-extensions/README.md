# Trikot.http swift extensions

To use `Trikot.http` swift extensions, you must export `http` module in your exported framework. See [Trikot.patron build.gradle file](https://github.com/mirego/trikot.patron/blob/master/common/build.gradle.kts) for a sample use case.

##### Setup Pod dependency

```groovy
    ENV['TRIKOT_FRAMEWORK_NAME']='ReplaceMeByTheFrameworkNameImportedByCocoaPods'
  pod 'Trikot/http', :git => 'git@github.com:mirego/trikot.git', :tag => properties['trikot_version']
```

Then, run `pod install`.

##### Setup HTTPRequestFactory Implementation based on `URLSession`

Trikot.http provides its own implementation based on `URLSession`.

```swift
import Trikot
...
HttpConfiguration().httpRequestFactory = TrikotHttpRequestFactory()
```

##### Setup connectivity publisher

```swift
import Trikot
...
HttpConfiguration().connectivityPublisher = TrikotConnectivityService.shared.publisher
...
TrikotConnectivityService.shared.start() // When app is running
...
TrikotConnectivityService.shared.stop() // When app is in background
```
