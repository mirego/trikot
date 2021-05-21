# Publishers and Processors

#### Implementation detail
* `Publisher` **never** emit a null value
* [PublisherExtentions](../streams/src/commonMain/kotlin/com/mirego/trikot/streams/reactive/PublisherExtensions.kt) offers a kotlin way to subscribe easily and use `processors`
* Publishers values and subscribers will be [frozen](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.native.concurrent/freeze.html) when switching from a thread to another.

#### PublisherExtensions
[PublisherExtentions.kt](../src/commonMain/kotlin/com/mirego/trikot/streams/reactive/PublisherExtensions.kt)  provides Kotlin methods of subscribing and using processors. Those methods heavily rely on [Cancelable and CancelableManager](./CANCELABLE.md). The sample in the documentation uses extensions.

## Publishers
Multiple publisher implementation can be instantiated depending on the use case you need to achieve.  

### BehaviorSubject
**Creates a [BehaviorSubject](http://reactivex.io/RxJava/javadoc/rx/subjects/BehaviorSubject.html)**

```kotlin
val publisher = Publishers.behaviorSubject<String>()
```

### PublishSubject
**Creates a [PublishSubject](http://reactivex.io/RxJava/javadoc/rx/subjects/PublishSubject.html)**

```kotlin
val publisher = Publishers.publishSubject<String>()
```

**Dispatch a new value to subscribers**
```kotlin
publisher.value = "new value"
```

**Subscribe to a publisher values**
```kotlin
val cancelableManager = CancelableManager()
val publisher = Publishers.behaviorSubject<String>()

publisher.subscribe(cancelableManager) { println(it) }
publisher.value = "foo"
publisher.value = "bar"

cancelableManager.cancel()
publisher.value = "Not dispatched because subscription is cancelled"
```

*Output*
```
foo
bar
```

**Subscribe to all publisher events**
```kotlin
publisher.subscribe(cancelableManager,
  onNext = { value -> println(value) }
  onError = { throwable -> println(throwable) }
  onCompleted = {}
```


### ExecutablePublisher
Executable publisher are specialized publisher that has to be `executed` before a value is dispatched. They are also `cancelable`

```kotlin
val publisher = object: BaseExecutablePublisher<String>() {
	override fun execute(cancelableManager: cancelableManager) {
		dispatchSuccess("foo")
	}
} 

publisher.execute()
```

### RefreshablePublisher
Refreshable publisher are specialized publisher that are reexecuted when calling `refresh`.

The callback block provides a cancellableManager and a boolean
- `cancellableManager`
	- A new cancellableManager is created on the first subscription and on any `refresh` call
	- Previous cancellableManager are cancelled when there is no subscription and on any `refresh` call
- `boolean`
	- `false`: when the block is triggered with the first subscription
	- `true`: when the block is triggered with the `refresh` method

```kotlin
val publisher = RefreshablePublisher({ cancellableManager, isRefreshing ->
	if (isRefreshing) {
		Publishers.behaviorSubject("I am refreshing")
	} else {
		Publishers.behaviorSubject("I am not refreshing")
	}
}

publisher.refresh()
```

### ColdPublisher
Cold publisher are specialized publishers that execute a block to create a publisher once subscribed too. 

```kotlin
ColdPublisher({ cancelableManager ->
	val myExecutablePublisher = .. .also{ cancelableManager.add(it) }
	myExecutablePublisher.execute()
	return myExecutablePublisher
})
```

## Processors
Processors alter the emission chain of publishers. 

#### Map
Map transform the value of a publisher
- *Input* - Value from previous processor
- *Output* - Transformed value

```kotlin
publisher.map { it.toString() }
```
This will transform the value to string

#### First
Dispatch the first value received from the publisher then cancel the subscription.
```kotlin
publisher.first()
```

#### Filter
Dispatch value only if it match the filter
```kotlin
publisher.filter { it.length > 2 }
```

#### Reject
Dispatch value only if it doesn't match the filter (the opposite of `filter` processor)
```kotlin
publisher.reject { it.empty() }
```

#### SwitchMap
*Input* - Value from previous processor
*Output* - Publisher
```kotlin
val publisherWhenOffline = Publishers.behaviorSubject<...>()
val publisherWhenOnline = Publishers.behaviorSubject<...>()

connectivityPublisher.switchMap { isConnected ->
	if (isConnected) publisherWhenOnline else publisherWhenOffline
}
```

Transform a value to a new publisher. When a new value is received, previous publisher is unsubscribed and new publisher is subscribed.

#### WithCancellableManager
Every time the publisher is notified, a `CancellableManager` is provided with the value. Previous `CancellableManager` are cancelled upon receiving a value
```kotlin
publisher.withCancellableManager().subscribe() { cancellableManager, value  ->
  cancellableManager.add(...)
}
```

#### ObserveOn
Allows to specify the Queue where publisher dispatch values
```kotlin
let myQueue = OperationDispatchQueue()
publisher.observeOn(myQueue).subscribe(...)
```
This will dispatch value, error and completion on the  myQueue  Worker/OperationQueue 

#### SubscribeOn
Allows to specify the Queue where subscription and cancellation occurs.
```kotlin
let myQueue = OperationDispatchQueue()
publisher.subscribeOn(myQueue).subscribe(...)
```
This will subscribe and cancel on the  myQueue  Worker/OperationQueue 

*Note*: `Configuration.serialSubscriptionDispatchQueue` make sure that only one subscription can be made. Useful to use when Thread safety need to be handled.

#### DistinctUntilChanged
Dispatch value only if it is not `equals` to the previous value
```kotlin
	publisher.distinctUntilChanged()
	publisher.value = "foo"	
	publisher.value = "foo"	
```
In this case, `foo` will only be emitted once.

#### OnErrorReturn
This processors convert the error dispatched by a publisher in a result. This allows subscription to stay open when an error is dispatched by the publisher.

```kotlin
	publisher.onErrorReturn { throwable ->
		Pair(null, throwable)
	}.map { successValue ->
		Pair(successValue, null)
	}.subscribe { successValue, error ->
		...
	}
```

#### Shared
Allows to share the result of previous transformation
```kotlin
val fooPublisher = Publishers.behaviorSubject("foo")
val uppercasePublisher = fooPublisher
	.map { it.toUppercase() }
	.map { it.toUppercase() }
	.shared()

uppercasePublisher.subscribe(...)
uppercasePublisher.subscribe(...)
uppercasePublisher.subscribe(...)
```
In this case,  when fooPublisher emit a new value, the maps will only be executed once.


#### Concat processor
Emits the values of the first publisher until it completes. Then emits the values for the nextPublisher.
```kotlin
val firstPublisher = Publishers.behaviorSubject("i")
val nextPublisher = Publishers.behaviorSubject("concat")

firstPublisher.concat(nextPublisher).subscribe(...) { println(it) }

firstPublisher.value = "love"
firstPublisher.complete()

```
Output will be "i" then "love" then "concat".

#### WithPreviousValue
Transform the new value in a oldValue -> newValue pair
```kotlin
val fooPublisher = Publishers.behaviorSubject("foo")

val fooPublisher.withPreviousValue().subscribe(...) { (oldValue, newValue) ->
	print("${oldValue} - ${newValue}")
}

fooPublisher.value = "bar"

```
Result:
```
null - foo
foo - bar
```

#### Debounce
Emit the new value only when a timeout has been reached. The timer resets on each emission.
```kotlin
val fooPublisher = Publishers.behaviorSubject("foo")
fooPublisher.debounce(500.milliseconds).subscribe(...) { println(it) }
fooPublisher.value = "bar"
```
In this case, "bar" will emit after 500 milliseconds.

### CombineLatest
CombineLatest combines the result of many publishers together. Is the list of publishers are the same type, emitted values will be typed. If not, emitted results will be of the Any? type.

**Combine 2 publishers**
```kotlin
val publisher1 = Publishers.behaviorSubject("a")
val publisher2 = Publishers.behaviorSubject("b")
publisher1.combine(publisher2).subscribe(cancellableManager) { (pub1Res, pub2Res) ->
	print("$pub1Res $pub2Res")
}
```
-> "a b"

**Combine many publishers**
```kotlin
val publisher1 = Publishers.behaviorSubject("a")
val publisher2 = Publishers.behaviorSubject("b")
val publisher3 = Publishers.behaviorSubject("c")
publisher1.combine(listOf(publisher2, publisher3)).subscribe(cancellableManager) { (pub1Res, pub2Res, pub3Res) ->
	print("$pub1Res $pub2Res $pub3Res")
}
```
-> "a b c"

**Combine a list of publishers**
```kotlin
combine(listOf(publisher1, publisher2, publisher3)).subscribe(cancellableManager) { (pub1Res, pub2Res, pub3Res) ->
	print("$pub1Res $pub2Res $pub3Res")
}
```
-> "a b c"

### RetryWhen
The RetryWhen operator responds to an `onError` notification from the source Publisher and emits a value
on the block input Publisher. If the block returned Publisher then emits any value, it will retry by resubscribing to the source Publisher.
If the block returned Publisher emits an error or completes, it will error/complete the source Publisher.

Note: This works well with ColdPublishers as resubscribing will re-call the ColdPublisher block and retry the operation.

**Retry on Specific error**
```kotlin
retryWhen { errors -> errors.map {
    when (it) {
        is SomeDomainException -> it
        else -> throw it
    }
}}
```

**Retry after refresh token**
```kotlin
originalPublisher.retryWhen { 
    it.switchMap { refresh() }
}
```

Other uses
- Retry after delay
- Retry after delay with exponential backoff

## Exceptions
Exceptions are not handled by default in `Trikot.streams`. To enforce that developers are handling exceptions the right way, they must explicitly catch their exceptions in the processors block and throw a  
`StreamsProcessorException`. This will result in dispatching an OnError to all Subscribers.    

Following code will crash
```kotlin
publisher1
    .map { throw Exception() }
    .subscribe(cancellableManager, 
            onNext = {}, 
            onError = { println(it) } 
        )
```

Following code will print the exception
```kotlin
publisher1
    .map { 
        try { 
            throw Exception() 
        } catch(e: Exception) { 
            throw StreamsProcessorException(e) 
        } 
    }
    .subscribe(cancellableManager, 
        onNext = {}, 
        onError = { println(it) } 
    )
```
