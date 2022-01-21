# Trikot.http

Kotlin Multiplaform http networking abstraction.

- Default Http request implementation for each platform
- Http header management and Error management
- Connectivity (Reachability) management
- Deserialization of results using [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization)
- Abstract network in Kotlin Multiplatform making sure every bit of network code is testable.
- Implement [Reactive Streams](http://www.reactive-streams.org/) pattern

## RequestBuilder

Request builder is a simple class that provides information about the request to send. See [RequestBuilder.kt](https://github.com/mirego/trikot.http/blob/master/http/src/commonMain/kotlin/com/mirego/trikot/http/RequestBuilder.kt) for fields documentation.

## HttpRequestPublisher

To send http requests

- Create a child class of HttpRequestPublisher
- Provide a RequestBuilder by overriding builder var
- Override processResponse to transform [HttpResponse](https://github.com/mirego/trikot.http/blob/master/http/src/commonMain/kotlin/com/mirego/trikot/http/HttpResponse.kt) into expected result type.

```kotlin
val request = object: HttpRequestPublisher<String>() {
    override val builder = RequestBuilder().also {
        it.baseUrl = "http://www.site.com/path/to/request"
    }

    override fun processResponse(response: HttpResponse): String = response.bodyString ?: ""
}
request.execute()
```

## DeserializableHttpRequestPublisher

To deserialize a JSON payload:

- Create a kotlix.serializable class
- Create a DeserializableHttpRequestPublisher with the serializer in parameter.

## NoResponseHttpRequestPublisher

If there is no need to parse the body of the response, use this publisher instead. Only errors will be handled.

## ResponseHttpRequestPublisher

If you want the raw HttpResponse object, use this publisher.

```kotlin
@Serializable
data class Foo(val bar: String)

val request = DeserializableHttpRequestPublisher<Foo>(
    Foo.serializer,
    RequestBuilder().also { it.path = "/getFoo" }
)
request.execute()
```

## HeaderProvider

`HttpRequestPublisher` and `DeserializableHttpRequestPublisher` both haves an optional `httpHeaderProvider` constructor parameter to provide additionnal request header (Authorization token per example).
See [HttpHeaderProvider.kt](https://github.com/mirego/trikot.http/blob/master/http/src/commonMain/kotlin/com/mirego/trikot/http/HttpHeaderProvider.kt) for complete documentation.

### ConnectivityPublisher

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
    dependencies {
        maven { url("https://s3.amazonaws.com/mirego-maven/public") }
    }

    ios() {
        binaries {
            framework {
                export "com.mirego.trikot:http:$trikot_version"
            }
        }
    }
    sourceSets {
        commonMain {
            dependencies {
                 implementation "com.mirego.trikot:http:$trikot_version"
            }
        }
    }
```

##### Setup platforms

- [Swift extensions](./swift-extensions/README.md)
