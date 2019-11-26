# Trikot.http

Multiplaform http networking abstraction.
- Default Http request implementation for each platform
- Solid Http Header management and Error management
- Connectivity (Reachability) management
- Deserialization of results using [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization)
- Abstract network in Kotlin Multiplatform making sure every bit of network code is testable. 

## Simple json request
```kotlin
    @Serializable
    data class Foo(val bar: String)

    val executablePublisher = DeserializableHttpRequestPublisher<Foo>(Foo.serializer,
        RequestBuilder().also { it.path = "/getFoo" }
    )
    executablePublisher.execute()
    result.subscribe(cancellableManager) { foo -> 
        print(foo.bar)
    }
```

# ConnectivityPublisher
When swift-extensions or android-ktx are configured (See below), you can access the ConnectivityState using the following sample:

```kotlin
HttpConfiguration.connectibityPublisher.subscribe(cancelableManager) {
    if it == NONE {
        print("No connection")
    } else {
        print("We have connection")
    }
}
```
Values are `WIFI`, `CELLULAR`, `NONE`


## Installation
##### Import dependencies
```groovy
    api "com.mirego.trikot:http:$trikot_http_version"
    jvm "com.mirego.trikot:http-jvm:$trikot_http_version"
    js "com.mirego.trikot:http-js:$trikot_http_version"
    iosx64 "com.mirego.trikot:http-iosx64:$trikot_http_version"
    iosarm64 "com.mirego.trikot:http-iosarm64:$trikot_http_version"
```

##### Setup platforms
- [Swift extensions](./swift-extensions/README.md)
- [Android extensions](./android-ktx/README.md) 

## License

Trikot.http is © 2018-2019 [Mirego](https://www.mirego.com) and may be freely distributed under the [New BSD license](http://opensource.org/licenses/BSD-3-Clause). See the [`LICENSE.md`](https://github.com/mirego/trikot.http/blob/master/LICENSE.md) file.

## About Mirego

[Mirego](https://www.mirego.com) is a team of passionate people who believe that work is a place where you can innovate and have fun. We’re a team of [talented people](https://life.mirego.com) who imagine and build beautiful Web and mobile applications. We come together to share ideas and [change the world](http://www.mirego.org).

We also [love open-source software](https://open.mirego.com) and we try to give back to the community as much as we can.
