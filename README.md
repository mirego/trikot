# Trikot.streams

Elegant implementation of ReactiveStreams for Kotlin Multiplatform.

- Manage object immutability in native implementation (Object are frozen when switching threads)
- Provide coroutines compatible queues (Threads) to all platform (except js) (Coroutines are not used by default)
- Simplify the management of publishers subscriptions and unsubscriptions
- Help you focus on what you need to do by hiding Multiplatform complexity


## Sample
#### Common code
```kotlin
class SearchController() {
    private val searchKeywordPublisher = Publishers.behaviorSubject<String>("keyword")
    private val searchResultsPublisher = searchKeywordPublisher.switchMap { keyword ->
        searchService.search(keyword)
    }.shared()
    val searchResultCount = searchResultsPublisher.switchMap { results ->
        PublisherFactory.create(results.count).toString()
    }
    val resultUppercaseTitles = searchResultsPublisher.map { it.title.toUpperCase() }
    fun searchFor(keyword: String) {
       searchKeywordPublisher.value = keyword 
    }
}
```

#### iOS
See [swift extensions](./swift-extensions/README.md) for more information.

Helps connect a publisher to a variable in a reactive environment.
```kotlin
let aStringPublisher = ...
let label = UILabel()
bind(aStringPublisher, \UILabel.text)
```

#### Android
Binding helpers relies on AndroidViewModel and uses lifecycleOwner to manage subscription and unsubscription. 
```kotlin
 <TextView
            ...
            android:text="@{aStringPublisher}"
            tools:text="0" />
```

## [Publishers and Processors](./documentation/PUBLISHERS.md)
Foundation of trikot.streams is based on a immutable and concurrent implementation of [Reactive-Streams](https://www.reactive-streams.org/).

## [Cancellables](./documentation/CANCELLABLE.md)
Subscription and unsubscription are managed trough `Cancellable` and `CancellableManager`.

## Installation
##### Import dependencies
```groovy
    api "com.mirego.trikot:streams:$trikot_streams_version"
    jvm "com.mirego.trikot:streams-jvm:$trikot_streams_version"
    js "com.mirego.trikot:streams-js:$trikot_streams_version"
    iosx64 "com.mirego.trikot:streams-iosx64:$trikot_streams_version"
    iosarm64 "com.mirego.trikot:streams-iosarm64:$trikot_streams_version"
```

## License

Trikot.streams is © 2018-2019 [Mirego](https://www.mirego.com) and may be freely distributed under the [New BSD license](http://opensource.org/licenses/BSD-3-Clause). See the [`LICENSE.md`](https://github.com/mirego/trikot.streams/blob/master/LICENSE.md) file.

## About Mirego

[Mirego](https://www.mirego.com) is a team of passionate people who believe that work is a place where you can innovate and have fun. We’re a team of [talented people](https://life.mirego.com) who imagine and build beautiful Web and mobile applications. We come together to share ideas and [change the world](http://www.mirego.org).

We also [love open-source software](https://open.mirego.com) and we try to give back to the community as much as we can.
