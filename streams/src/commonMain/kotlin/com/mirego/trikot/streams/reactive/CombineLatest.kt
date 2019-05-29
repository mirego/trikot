package com.mirego.trikot.streams.reactive

import com.mirego.trikot.streams.cancelable.Cancelable
import com.mirego.trikot.streams.cancelable.CancelableManager
import com.mirego.trikot.streams.concurrent.AtomicReference
import org.reactivestreams.Publisher

abstract class CombineLatestResult<A, B, C, D, E>(
    open var _1: A? = null,
    open var _2: B? = null,
    open var _3: C? = null,
    open var _4: D? = null,
    open var _5: E? = null
) {
    abstract fun shouldDispatch(): Boolean
    abstract fun copyForUpdate(): CombineLatestResult<A, B, C, D, E>
}

data class CombineLatestResult2<A, B>(override var _1: A? = null, override var _2: B? = null) :
    CombineLatestResult<A, B, Any, Any, Any>() {
    override fun copyForUpdate(): CombineLatestResult<A, B, Any, Any, Any> {
        return copy()
    }

    override fun shouldDispatch(): Boolean = _1 != null && _2 != null
}

data class CombineLatestResult3<A, B, C>(
    override var _1: A? = null,
    override var _2: B? = null,
    override var _3: C? = null
) : CombineLatestResult<A, B, C, Any, Any>() {
    override fun copyForUpdate(): CombineLatestResult<A, B, C, Any, Any> {
        return copy()
    }

    override fun shouldDispatch(): Boolean = _1 != null && _2 != null && _3 != null
}

data class CombineLatestResult4<A, B, C, D>(
    override var _1: A? = null,
    override var _2: B? = null,
    override var _3: C? = null,
    override var _4: D? = null
) : CombineLatestResult<A, B, C, D, Any>() {
    override fun copyForUpdate(): CombineLatestResult<A, B, C, D, Any> {
        return copy()
    }

    override fun shouldDispatch(): Boolean = _1 != null && _2 != null && _3 != null && _4 != null
}

data class CombineLatestResult5<A, B, C, D, E>(
    override var _1: A? = null,
    override var _2: B? = null,
    override var _3: C? = null,
    override var _4: D? = null,
    override var _5: E? = null
) : CombineLatestResult<A, B, C, D, E>() {
    override fun copyForUpdate(): CombineLatestResult<A, B, C, D, E> {
        return copy()
    }

    override fun shouldDispatch(): Boolean = _1 != null && _2 != null && _3 != null && _4 != null && _5 != null
}

class CombineLatest<R : CombineLatestResult<A, B, C, D, E>, A, B, C, D, E>(
    result: R,
    private var pub1: Publisher<A>,
    private var pub2: Publisher<B>,
    private var pub3: Publisher<C>? = null,
    private var pub4: Publisher<D>? = null,
    private var pub5: Publisher<E>? = null
) : SimplePublisher<R>(null), Cancelable {
    private var masterCancelableManager = CancelableManager()
    private var cancelableManager = AtomicReference(CancelableManager())
    private var result = AtomicReference(result)

    override fun onFirstSubscription() {
        super.onFirstSubscription()
        cancelableManager.value.cancel()
        val newCancelableManager = CancelableManager().also { masterCancelableManager.add(it) }
        cancelableManager.setOrThrow(cancelableManager.value, newCancelableManager)

        pub1.subscribe(
            newCancelableManager,
            onNext = { onNewValue { newResult -> newResult._1 = it } },
            onError = { dispatchError(it) })
        pub2.subscribe(
            newCancelableManager,
            onNext = { onNewValue { newResult -> newResult._2 = it } },
            onError = { dispatchError(it) })
        pub3?.subscribe(
            newCancelableManager,
            onNext = { onNewValue { newResult -> newResult._3 = it } },
            onError = { dispatchError(it) })
        pub4?.subscribe(
            newCancelableManager,
            onNext = { onNewValue { newResult -> newResult._4 = it } },
            onError = { dispatchError(it) })
        pub5?.subscribe(
            newCancelableManager,
            onNext = { onNewValue { newResult -> newResult._5 = it } },
            onError = { dispatchError(it) })
    }

    fun dispatchIfNeeded() {
        if (result.value.shouldDispatch()) {
            value = result.value
        }
    }

    fun dispatchError(error: Throwable) {
        masterCancelableManager.cancel()
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
        cancelableManager.value.cancel()
    }

    override fun cancel() {
        masterCancelableManager.cancel()
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
