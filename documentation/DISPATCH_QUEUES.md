## trikot.foundation.concurrent.dispatchqueue
The current state of Coroutines is not well suited for Kotlin native. See [issue 462](https://github.com/Kotlin/kotlinx.coroutines/issues/462)

As a workaround, Trikot provides Threads (Android Worker, iOS Operation Queue) for running background operations. They are called DispatchQueues.

Once KMP memory model will be addressed, default dispatch queues provided by Trikot will be migrated to Coroutines. See Using Coroutines section below for our migration strategy.

### OperationDispatchQueue
```
val dispatchQueue = OperationDispatchQueue()
dispatchQueue.dispatch {
	// Heavy cpu operation
}
```

### OperationDispatchQueue
- Dispatch queue for background operations. Number of threads depends on the platform implementation and the number of processors

### SequentialDispatchQueue
- Special queue that ensure than operation will be executed sequentially. Will be executed synchronously if no operation are currently running.

### SynchronousDispatchQueue
- Dispatch operation synchronously. Usefull for testing.

### Default provided queues
By default, trikot provide those dispatch queues available trough the Configuration object
* FoundationConfiguration.publisherExecutionDispatchQueue -> Default queue used by `Trikot.streams` to execute code
* FoundationConfiguration.serialSubscriptionDispatchQueue -> Queue that ensure Serial execution along your systems.

### QueueDispatcher
Like Coroutines, one class can implement `QueueDispatcher` and dispatch event using
```kotlin
class Foo(val dispatchQueue: DispatchQueue
): QueueDispatcher {
	fun bar() {
		dispatch {
		   // Stuff to run on other thread
		}
	}
}
```

### Using coroutines
The following code runs ExecutablePublisher on coroutines instead of threads

```kotlin
class MyCoroutineDispatcher(val context: CoroutineContext = Dispatchers.unconfined): DispatchQueue, CoroutineScope {
	fun dispatch(block: DispatchBlock) {
		launch {
			block()
		}
	}
}

FoundationConfiguration.publisherExecutionDispatchQueue = MyCoroutineDispatcher()

```
