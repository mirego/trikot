package com.mirego.trikot.streams.reactive

import com.mirego.trikot.streams.cancellable.Cancellable
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.foundation.concurrent.AtomicReference
import org.reactivestreams.Publisher
import kotlin.js.JsName

abstract class CombineLatestResult<A, B, C, D, E>(
    @JsName("c1")
    open var component1: A? = null,
    @JsName("c2")
    open var component2: B? = null,
    @JsName("c3")
    open var component3: C? = null,
    @JsName("c4")
    open var component4: D? = null,
    @JsName("c5")
    open var component5: E? = null
) {
    abstract fun shouldDispatch(): Boolean
    abstract fun copyForUpdate(): CombineLatestResult<A, B, C, D, E>
}

data class CombineLatestResult2<A, B>(override var component1: A? = null, override var component2: B? = null) :
    CombineLatestResult<A, B, Any, Any, Any>() {
    override fun copyForUpdate(): CombineLatestResult<A, B, Any, Any, Any> {
        return copy()
    }

    override fun shouldDispatch(): Boolean = component1 != null && component2 != null
}

data class CombineLatestResult3<A, B, C>(
    override var component1: A? = null,
    override var component2: B? = null,
    override var component3: C? = null
) : CombineLatestResult<A, B, C, Any, Any>() {
    override fun copyForUpdate(): CombineLatestResult<A, B, C, Any, Any> {
        return copy()
    }

    override fun shouldDispatch(): Boolean = component1 != null && component2 != null && component3 != null
}

data class CombineLatestResult4<A, B, C, D>(
    override var component1: A? = null,
    override var component2: B? = null,
    override var component3: C? = null,
    override var component4: D? = null
) : CombineLatestResult<A, B, C, D, Any>() {
    override fun copyForUpdate(): CombineLatestResult<A, B, C, D, Any> {
        return copy()
    }

    override fun shouldDispatch(): Boolean = component1 != null && component2 != null && component3 != null && component4 != null
}

data class CombineLatestResult5<A, B, C, D, E>(
    override var component1: A? = null,
    override var component2: B? = null,
    override var component3: C? = null,
    override var component4: D? = null,
    override var component5: E? = null
) : CombineLatestResult<A, B, C, D, E>() {
    override fun copyForUpdate(): CombineLatestResult<A, B, C, D, E> {
        return copy()
    }

    override fun shouldDispatch(): Boolean = component1 != null && component2 != null && component3 != null && component4 != null && component5 != null
}

class CombineLatest<R : CombineLatestResult<A, B, C, D, E>, A, B, C, D, E>(
    result: R,
    private var pub1: Publisher<A>,
    private var pub2: Publisher<B>,
    private var pub3: Publisher<C>? = null,
    private var pub4: Publisher<D>? = null,
    private var pub5: Publisher<E>? = null
) : BehaviorSubjectImpl<R>(null), Cancellable {
    private var masterCancellableManager = CancellableManager()
    private var cancellableManager = AtomicReference(CancellableManager())
    private var result = AtomicReference(result)

    override fun onFirstSubscription() {
        super.onFirstSubscription()
        cancellableManager.value.cancel()
        val newCancellableManager = CancellableManager().also { masterCancellableManager.add(it) }
        cancellableManager.setOrThrow(cancellableManager.value, newCancellableManager)

        pub1.subscribe(
            newCancellableManager,
            onNext = { onNewValue { newResult -> newResult.component1 = it } },
            onError = { dispatchError(it) })
        pub2.subscribe(
            newCancellableManager,
            onNext = { onNewValue { newResult -> newResult.component2 = it } },
            onError = { dispatchError(it) })
        pub3?.subscribe(
            newCancellableManager,
            onNext = { onNewValue { newResult -> newResult.component3 = it } },
            onError = { dispatchError(it) })
        pub4?.subscribe(
            newCancellableManager,
            onNext = { onNewValue { newResult -> newResult.component4 = it } },
            onError = { dispatchError(it) })
        pub5?.subscribe(
            newCancellableManager,
            onNext = { onNewValue { newResult -> newResult.component5 = it } },
            onError = { dispatchError(it) })
    }

    fun dispatchIfNeeded() {
        if (result.value.shouldDispatch()) {
            value = result.value
        }
    }

    fun dispatchError(error: Throwable) {
        masterCancellableManager.cancel()
        this.error = error
    }

    fun onNewValue(updateBlock: (CombineLatestResult<A, B, C, D, E>) -> Unit) {
        val newResult = result.value.copyForUpdate()
        updateBlock(newResult)
        @Suppress("UNCHECKED_CAST")
        result.setOrThrow(result.value, newResult as R)
        dispatchIfNeeded()
    }

    override fun onNoSubscription() {
        super.onNoSubscription()
        cancellableManager.value.cancel()
    }

    override fun cancel() {
        masterCancellableManager.cancel()
    }

    companion object {
        fun <A, B> combine2(
            pub1: Publisher<A>,
            pub2: Publisher<B>
        ): Publisher<CombineLatestResult2<A, B>> {
            return CombineLatest(CombineLatestResult2(), pub1, pub2)
        }

        fun <A, B, C> combine3(
            pub1: Publisher<A>,
            pub2: Publisher<B>,
            pub3: Publisher<C>
        ): Publisher<CombineLatestResult3<A, B, C>> {
            return CombineLatest(CombineLatestResult3(), pub1, pub2, pub3)
        }

        fun <A, B, C, D> combine4(
            pub1: Publisher<A>,
            pub2: Publisher<B>,
            pub3: Publisher<C>,
            pub4: Publisher<D>
        ): Publisher<CombineLatestResult4<A, B, C, D>> {
            return CombineLatest(CombineLatestResult4(), pub1, pub2, pub3, pub4)
        }

        fun <A, B, C, D, E> combine5(
            pub1: Publisher<A>,
            pub2: Publisher<B>,
            pub3: Publisher<C>,
            pub4: Publisher<D>,
            pub5: Publisher<E>
        ): Publisher<CombineLatestResult5<A, B, C, D, E>> {
            return CombineLatest(CombineLatestResult5(), pub1, pub2, pub3, pub4, pub5)
        }
    }
}
