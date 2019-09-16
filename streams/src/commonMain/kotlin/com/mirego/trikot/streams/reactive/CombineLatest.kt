package com.mirego.trikot.streams.reactive

import com.mirego.trikot.streams.reactive.processors.combine
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

data class CombineLatestResult2<A, B>(
    override var component1: A? = null,
    override var component2: B? = null
) :
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

    override fun shouldDispatch(): Boolean =
        component1 != null && component2 != null && component3 != null
}

class CombineLatest {
    companion object {
        @Deprecated("This method is deprecated, use CombineLatestProcessor")
        fun <A, B> combine2(
            pub1: Publisher<A>,
            pub2: Publisher<B>
        ): Publisher<CombineLatestResult2<A, B>> {
            return pub1.combine(pub2).map { CombineLatestResult2(it.first, it.second) }
        }

        @Deprecated("This method is deprecated, use CombineLatestProcessor")
        fun combine3(
            pub1: Publisher<Any>,
            pub2: Publisher<Any>,
            pub3: Publisher<Any>
        ): Publisher<CombineLatestResult3<Any, Any, Any>> {
            return combine(listOf(pub1, pub2, pub3)).map {
                CombineLatestResult3(it[0], it[1], it[2])
            }
        }
    }
}
