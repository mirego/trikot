# Publishers and Processors

#### Implementation detail
* `Publisher` **never** emit a null value
* [PublisherExtentions](../streams/src/commonMain/kotlin/com/mirego/trikot/streams/reactive/PublisherExtensions.kt) offers a kotlin way to subscribe easily and use `processors`
* Publishers values and subscribers will be [frozen](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.native.concurrent/freeze.html) when switching from a thread to another.

#### PublisherExtensions
[PublisherExtentions.kt](../src/commonMain/kotlin/com/mirego/trikot/streams/reactive/PublisherExtensions.kt)  provides Kotlin methods of subscribing and using processors. Those methods heavily rely on [Cancelable and CancelableManager](./CANCELABLE.md). The sample in the documentation uses extensions.

## Publishers
Multiple publisher implementation can be instantiated depending on the use case you need to achieve.  

### SimplePublisher
**Create a publisher**
```kotlin
val publisher = PublisherFactory.create<String>()
```

**Dispatch a new value to subscribers**
```kotlin
publisher.value = "new value"
```

**Subscribe to a publisher values**
```kotlin
val cancelableManager = CancelableManager()
val publisher = PublisherFactory.create<String>()

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

In this case, executablePublisher will emit `foo` once executed.

**Result methods**
- `dispatchSuccess("successValue")`
- `diapatchError(throwable)`

In both cases, `onCompleted` will be emitted afterward and subscription will be cancelled.


### ColdPublisher
Cold publisher are specialized publishers that execute a block to create a publisher once subscribed too. 

```kotlin
ColdPublisher({ cancelableManager ->
	val myExecutablePublisher = .. .also{ cancelableManager.add(it) }
	myExecutablePublisher.execute()
	return myExecutablePublisher
})
```

### CombineLatest
CombineLatest combines the result of up to 5 publishers and dispatch them when all publishers have a value. CombineLatest values are destructurizable data class. Use the provided companion object to create a new CombineLatest publisher: `CombineLatest.combine[X](...): CombineLatest`

Example with destructuring.
```kotlin
CombineLatest.combine2(publisher1, publisher2)
	.subscribe(cancelableManager) { (publisher1Value, publisher2Value) ->
	// Do something with the result
}
```

Example without destructuring.
```kotlin
CombineLatest.combine4(publisher1, publisher2, publisher3, publisher4)
	.subscribe(cancelableManager) { combineLatestResult4 ->
	val publisher1Value = combineLatestResult4.component1
	val publisher3Value = combineLatestResult4.component3
}
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
Dispatch the value only if the value match the filter

#### SwitchMap
*Input* - Value from previous processor
*Output* - Publisher
```kotlin
val publisherWhenOffline = PublisherFactory.create<...>()
val publisherWhenOnline = PublisherFactory.create<...>()

connectivityPublisher.switchMap { isConnected ->
	if (isConnected) publisherWhenOnline else publisherWhenOffline
}
```

Transform a value to a new publisher. When a new value is received, previous publisher is unsubscribed and new publisher is subscribed.

#### WithChildCancelableManager
Every time the publisher is notified, a `CancelableManager` is provided with the value. Previous `CancelableManager` are cancelled upon receiving a value
```kotlin
publisher.withChildCancelableManager { value, cancelableManager ->
  cancelableManager.add(...)
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

#### Shared
Allows to share the result of previous transformation
```kotlin
val fooPublisher = PublisherFactory.create("foo")
val uppercasePublisher = fooPublisher
	.map { it.toUppercase() }
	.map { it.toUppercase() }
	.shared()

uppercasePublisher.subscribe(...)
uppercasePublisher.subscribe(...)
uppercasePublisher.subscribe(...)
```
In this case,  when fooPublisher emit a new value, the maps will only be executed once.
