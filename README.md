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
    private val searchKeywordPublisher = PublisherFactory.create<String>("keyword")
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

#### JS
```
Not implemented yet
```

## [Publishers and Processors](./documentation/PUBLISHERS.md)
Foundation of trikot.streams is based on a immutable and concurrent implementation of [Reactive-Streams](https://www.reactive-streams.org/).

See the documentation [here](./documentation/PUBLISHERS.md)

## [Cancellables](./documentation/CANCELLABLE.md)
Subscription and unsubscription are managed trough `Cancellable` and `CancellableManager`.

See the documentation [here](./documentation/CANCELABLE.md)

## [Dispatch Queues](./documentation/DISPATCH_QUEUES.md)
Dispatch queues are the threading model of Trikot.streams

See the documentation [here](./documentation/DISPATCH_QUEUES.md)
