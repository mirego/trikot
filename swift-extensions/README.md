# Trikot.http swift extensions
To use `Trikot.http` swift extensions, you must export `http` and `http-iosx64` module in your exported framework. See [Trikot.patron build.gradle file](https://github.com/mirego/trikot.patron/blob/master/common/build.gradle) for a sample use case.

##### Setup Pod dependency
```groovy
    ENV['TRIKOT_FRAMEWORK_NAME']='ReplaceMeByTheFrameworkNameImportedByCocoaPods'
  pod 'Trikot.http', :git => 'git@github.com:mirego/trikot.http.git'
```
Then, run `pod install`.

##### Setup HTTPRequestFactory Implementation based on `URLSession`
Trikot.http provides its own implementation based on `URLSession`.

```swift
import Trikot_http
...
HttpConfiguration().httpRequestFactory = TrikotHttpRequestFactory()
```

##### Setup connectivity publisher
```swift
import Trikot_http
...
HttpConfiguration().connectivityPublisher = TrikotConnectivityService.shared.publisher
...
TrikotConnectivityService.shared.start() // When app is running
...
TrikotConnectivityService.shared.stop() // When app is in background
```

##### Notes on Ktor
As of Kotlin 1.3.50, `Ktor` only runs on the mainThread limiting how requests are processed and content parsed. When Coroutines multithreading issues will be adressed in Kotlin Native, TrikotHttpRequestFactory might switch to a Ktor implementation.
