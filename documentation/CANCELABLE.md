# Cancelable and CancelableManager

One key concept of Trikot is the `Cancelable`s.  `Cancelable` are meant to be added into a `CancelableManager` that manage their lifecycle. When `CancelableManager.cancel()` is called, all `Cancelable`  are cancelled.

*Implementation detail*
* When cancelled, any `Cancelable` added  to the `CancelableManager` are automatically cancelled.
* Once cancelled are not meant to be reuse after cancel has been called.


```
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
