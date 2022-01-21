# Trikot.streams

Elegant implementation of ReactiveStreams for Kotlin Multiplatform.

- Manage object immutability in native implementation (Object are frozen when switching threads)
- Multithread support with ObserveOn and SubscribeOn processors
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
    val searchResultCountLabel = searchResultsPublisher.map { "${it.results.count()} results" }
    fun searchFor(keyword: String) {
       searchKeywordPublisher.value = keyword 
    }
}
```

#### Swift
See [swift extensions](./swift-extensions/README.md) for more information.

Helps connect a publisher to a variable in a reactive environment.
```kotlin
let label = UILabel()
label.bind(searchController.searchResultCountLabel, \UILabel.text)
```

#### Android
See [android-ktx](./android-ktx/README.md) for more information.

Binding helpers relies on AndroidViewModel and uses lifecycleOwner to manage subscription and unsubscription. 
```kotlin
val searchResultLiveData = searchController.searchResultCountLabel.asLiveData()
```

## [Publishers and Processors](./documentation/PUBLISHERS.md)
Foundation of trikot.streams is based on a immutable and concurrent implementation of [Reactive-Streams](https://www.reactive-streams.org/).

## [Cancellables](./documentation/CANCELLABLE.md)
Subscription and unsubscription are managed trough `Cancellable` and `CancellableManager`.

## Installation
##### Import dependencies
```groovy
   dependencies {
       maven { url('https://s3.amazonaws.com/mirego-maven/public') }
   }

    ios() {
        binaries {
            framework {
                export "com.mirego.trikot:streams:$trikot_version"
            }
        }
    }
    sourceSets {
        commonMain {
            dependencies {
                 implementation "com.mirego.trikot:streams:$trikot_version"
            }
        }
    }
```

## License

Trikot.streams is © 2018-2019 [Mirego](https://www.mirego.com) and may be freely distributed under the [New BSD license](http://opensource.org/licenses/BSD-3-Clause). See the [`LICENSE.md`](https://github.com/mirego/trikot.streams/blob/master/LICENSE.md) file.

## About Mirego

[Mirego](https://www.mirego.com) is a team of passionate people who believe that work is a place where you can innovate and have fun. We’re a team of [talented people](https://life.mirego.com) who imagine and build beautiful Web and mobile applications. We come together to share ideas and [change the world](http://www.mirego.org).

We also [love open-source software](https://open.mirego.com) and we try to give back to the community as much as we can.
