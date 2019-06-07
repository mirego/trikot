# Cancellable and CancellableManager

One key concept of Trikot is the `Cancellable`s. `Cancellable` are meant to be added into a `CancellableManager` that manage their lifecycle. When `CancellableManager.cancel()` is called, all `Cancellable`  are cancelled.

*Implementation detail*
* When cancelled, any `Cancellable` added  to the `CancellableManager` are automatically cancelled.
* Once cancelled are not meant to be reuse after cancel has been called.


```kotlin
class Bar: Cancellable {
	override fun cancel() {
		// Terminate all operations
	}
}

class Foo {
	private val cancellableManager = CancellableManager()
	private val Bar().also { cancellableManager.add(it) }

	fun doCompleted() {
		cancellableManager.cancel()
	}
}
```

### CancellableManagerProvider
It's a common pattern to cancel a CancellableManager and create a new CancellableManager to replace it when resetting a state. This is the main use case of CancellableManagerProvider.

*Implementation detail*
- `CancellableManagerProvider` is a cancelable so it can be added to any other CancellableManager.
- Once cancelled, provided cancellableManager are automatically cancelled.

Calling cancelPreviousAndCreate will cancel any previous `CancellableManager` returned and provide a new one
```kotlin
val cancellableManagerProvider = CancellableManagerProvider()
val cancelableManager = CancellableManagerProvider.cancelPreviousAndCreate()
cancelableManager.add(...)
```

