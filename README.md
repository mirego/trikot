# Trikot.http
**Incubating**

Multiplaform http networking abstraction.
- Default implementation uses [ktor](https://ktor.io/) underneath
- Advanced Http Header management
- Connectivity (Reachability) management
- Deserialization of results using [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization)


## Sample Http request with deserialization usage
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

