## trikot.streams.concurrent.dispatchqueue
As Coroutines are not really well supported by Kotlin Native (They always run on main thread for now), this library uses Threads (Android Worker, iOS Operation Queue)  for running background operations.

Like Coroutines, one class can implement `QueueDispatcher` and dispatch event using
```
class Foo(val dispatchQueue: DispatchQueue
): QueueDispatcher {
	fun bar() {
		dispatch {
		   // Stuff to run on other thread
		}
	}
}
```

*Note*: `CoroutineDispatchQueue` is a provided implementation of a dispatchQueue using Coroutines. 

By default, trikot provide those dispatch queues available trough the Configuration object
* Configuration.publisherExecutionDispatchQueue -> Default queue used by `ExecutablePublisher` to execute code
* Configuration.serialSubscriptionDispatchQueue -> Single not shared thread useful to subscribe concurrently to publishers.
