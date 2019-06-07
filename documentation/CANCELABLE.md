# Cancelable and CancelableManager

One key concept of Trikot is the `Cancelable`s.  `Cancelable` are meant to be added into a `CancelableManager` that manage their lifecycle. When `CancelableManager.cancel()` is called, all `Cancelable`  are cancelled.

*Implementation detail*
* When cancelled, any `Cancelable` added  to the `CancelableManager` are automatically cancelled.
* Once cancelled are not meant to be reuse after cancel has been called.


```kotlin
class Bar: Cancelable {
	override fun cancel() {
		// Terminate all operations
	}
}

class Foo {
	private val cancelableManager = CancelableManager()
	private val Bar().also { cancelableManager.add(it) }

	fun doCompleted() {
		cancelableManager.cancel()
	}
}
```

### ResettableCancelableManager
It's a common pattern to cancel a CancelableManager and create a new CancelableManager to replace it when reseting a state. This is the main use case of ResettableCancelableManager.

*Implementation detail*
- `ResettableCancelableManager` is a cancelable so it can be added to any other CancelableManager
- Once cancelled it cannot be resetted

Calling reset will cancel any previous `CancelableManager` returned and provide a new one
```kotlin
val resettableCancelableManager = ResettableCancelableManager()
val cancelableManager = resettableCancelableManager.reset()
cancelableManager.add(...)
```

