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
```
val publisher = PublisherFactory.create<String>()
```

**Dispatch a new value to subscribers**
```
publisher.value = "new value"
```

**Subscribe to a publisher values**
```
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
```
publisher.subscribe(cancelableManager,
  onNext = { value -> println(value) }
  onError = { throwable -> println(throwable) }
  onCompleted = {}
```


### ExecutablePublisher
Executable publisher are specialized publisher that has to be `executed` before a value is dispatched. They are also `cancelable`

```
val publisher = object: BaseExecutablePublisher<String>() {
	override fun execute(cancelableManager: cancelableManager) {
		dispatchSuccess("foo")
	}
} 

publisher.execute()
```

In this case,  executablePublisher will emit `foo` once executed.

**Result methods**
- `dispatchSuccess("successValue")`
- `diapatchError(throwable)`

In both cases, `onCompleted` will be emitted afterward and subscription will be cancelled.


### ColdPublisher
Cold publisher are specialized publishers that execute a block to create a publisher once subscribed too. 

```
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

```
publisher.map { it.toString() }
```
This will transform the value to string

#### First
Dispatch the first value received from the publisher then cancel the subscription.
```
publisher.first()
```

#### Filter
Dispatch value only if it match the filter
```
publisher.filter { it.length > 2 }
```
Dispatch the value only if the value match the filter

#### SwitchMap
*Input* - Value from previous processor
*Output* - Publisher
```
val publisherWhenOffline = PublisherFactory.create<...>()
val publisherWhenOnline = PublisherFactory.create<...>()

connectivityPublisher.switchMap { isConnected ->
	if (isConnected) publisherWhenOnline else publisherWhenOffline
}
```

Transform a value to a new publisher. When a new value is received, previous publisher is unsubscribed and new publisher is subscribed.

#### WithChildCancelableManager
Every time the publisher is notified, a `CancelableManager` is provided with the value. Previous `CancelableManager` are cancelled upon receiving a value
```
publisher.withChildCancelableManager { value, cancelableManager ->
  cancelableManager.add(...)
}
```

#### ObserveOn
Allows to specify the Queue where publisher dispatch values
```
let myQueue = OperationDispatchQueue()
publisher.observeOn(myQueue).subscribe(...)
```
This will dispatch value, error and completion on the  myQueue  Worker/OperationQueue 

#### SubscribeOn
Allows to specify the Queue where subscription and cancellation occurs.
```
let myQueue = OperationDispatchQueue()
publisher.subscribeOn(myQueue).subscribe(...)
```
This will subscribe and cancel on the  myQueue  Worker/OperationQueue 

*Note*: `Configuration.serialSubscriptionDispatchQueue` make sure that only one subscription can be made. Useful to use when Thread safety need to be handled.

#### Shared
Allows to share the result of previous transformation
```
val fooPublisher = PublisherFactory.create("foo")
val uppercasePublisher = fooPublisher
	.map { it.toUppercase() }
	.map { it.toUppercase() }
	.shared()

uppercasePublisher.subsribe(...)
uppercasePublisher.subsribe(...)
uppercasePublisher.subsribe(...)
```
In this case,  when fooPublisher emit a new value, the maps will only be executed once.
