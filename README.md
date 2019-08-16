# Trikot.http

Multiplaform http networking abstraction.
- Default implementation uses [ktor](https://ktor.io/) underneath
- Advanced Http Header management
- Connectivity (Reachability) management
- Deserialization of results using [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization)


## Sample usage
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

## Installation
##### Import dependencies
```groovy
    api "com.mirego.trikot:http:$trikot_kword_version"
    jvm "com.mirego.trikot:http-jvm:$trikot_kword_version"
    js "com.mirego.trikot:http-js:$trikot_kword_version"
    iosx64 "com.mirego.trikot:http-iosx64:$trikot_kword_version"
    iosarm64 "com.mirego.trikot:http-iosarm64:$trikot_kword_version"
```
##### Setup platforms
- [Swift extensions](./swift-extensions/README.md)
- [Android extensions]() 


# HttpRequestPublisher Class
When executed, request headers are retreived from the HttpHeaderProvider, Request is sent with the provided header. If an error occurs, HttpHeaderProvider is notified that the request has failed (so it can clear some header if needed). As `HttpRequestPublisher` is an [ExecutablePublisher](https://github.com/mirego/trikot.streams/blob/master/documentation/PUBLISHERS.md), the sucess, error and completion value are reveived by subscribing to the `HttpRequestPublisher`.

HttpRequestPublisher is abstract is meant to be overriden.

# DeserializableHttpRequestPublisher Class
Implementation of `HttpRequestPublisher` that publish a deserialized response of the http operation.

### Constructor parameter
- `deserializer`: kolinx.deserializer
- `builder`: Request to send (See below for detail)
- `headerProvider`: `HttpHeaderProvider`, default is `HttpConfiguration.defaultHttpHeaderProvider`

# Request builder
Class use to hold the payload of a request

### Parameters
- `baseUrl`: http://my.site.com/api
- `path`: /user/1
- `method`: HttpMethod to use. Supported values are `GET`, `POST`, `PUT`, `PATCH`, `DELETE`, `HEAD`
- `headers`: Headers to include in your query. Will be merged with the headers provided by `HttpHeaderProvider`.
- `body`: Body to send with the request. Only strings are supported at the moment.
- `cachePolicy`: Values are `USE_PROTOCOL_CACHE_POLICY`, `RELOAD_IGNORING_CACHE_DATA`


# ConnectivityPublisher
Values are `WIFI`, `CELLULAR`, `NONE`
```kotlin
HttpConfiguration.connectibityPublisher.subscribe(cancelableManager) {
    if it == NONE {
        print("No connection")
    } else {
        print("We have connection")
    }
}
```

By default, Connectivity publisher always publish `WIFI`. Both iOS and Android provide a publisher implementation. See below on own it be configured.

# HttpConfiguration
- `httpRequestFactory`: `HttpRequestFactory` that will be used internally to send the requests.
- `networkDispatchQueue`: Threads where the network requests are sent. **Note**: Result dispatching and deserialization are done on `Configuration.operationQueue` threads.
- `defaultHttpHeaderProvider`: Default header provider 
- `connectivityPublisher`: Connectivity publisher (Must be provided by the platform)
- `baseUrl`: Default base URL to be used if the request builder did not set any
