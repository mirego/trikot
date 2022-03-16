/*
 * Copyright 2016-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("unused")

package kotlinx.coroutines.reactive

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext
import kotlin.test.assertTrue
import kotlin.test.fail

expect val isStressTest: Boolean
expect val stressTestMultiplier: Int
expect val stressTestMultiplierSqrt: Int

/**
 * The result of a multiplatform asynchronous test.
 * Aliases into Unit on K/JVM and K/N, and into Promise on K/JS.
 */
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect class TestResult

expect val isNative: Boolean

expect open class TestBase constructor() {
    /*
     * In common tests we emulate parameterized tests
     * by iterating over parameters space in the single @Test method.
     * This kind of tests is too slow for JS and does not fit into
     * the default Mocha timeout, so we're using this flag to bail-out
     * and run such tests only on JVM and K/N.
     */
    val isBoundByJsTestTimeout: Boolean
    fun error(message: Any, cause: Throwable? = null): Nothing
    fun expect(index: Int)
    fun expectUnreached()
    fun finish(index: Int)
    fun ensureFinished() // Ensures that 'finish' was invoked
    fun reset() // Resets counter and finish flag. Workaround for parametrized tests absence in common

    fun runTest(
        expected: ((Throwable) -> Boolean)? = null,
        unhandled: List<(Throwable) -> Boolean> = emptyList(),
        block: suspend CoroutineScope.() -> Unit
    ): TestResult
}

suspend fun currentDispatcher() = coroutineContext[ContinuationInterceptor]!!

suspend inline fun hang(onCancellation: () -> Unit) {
    try {
        suspendCancellableCoroutine<Unit> { }
    } finally {
        onCancellation()
    }
}

inline fun <reified T : Throwable> assertFailsWith(block: () -> Unit) {
    try {
        block()
        error("Should not be reached")
    } catch (e: Throwable) {
        assertTrue(e is T)
    }
}

suspend inline fun <reified T : Throwable> assertFailsWith(flow: Flow<*>) {
    try {
        flow.collect()
        fail("Should be unreached")
    } catch (e: Throwable) {
        assertTrue(e is T, "Expected exception ${T::class}, but had $e instead")
    }
}

suspend fun Flow<Int>.sum() = fold(0) { acc, value -> acc + value }
suspend fun Flow<Long>.longSum() = fold(0L) { acc, value -> acc + value }

// data is added to avoid stacktrace recovery because CopyableThrowable is not accessible from common modules
class TestException(message: String? = null, private val data: Any? = null) : Throwable(message)
class TestException1(message: String? = null, private val data: Any? = null) : Throwable(message)
class TestException2(message: String? = null, private val data: Any? = null) : Throwable(message)
class TestException3(message: String? = null, private val data: Any? = null) : Throwable(message)
class TestCancellationException(message: String? = null, private val data: Any? = null) : CancellationException(message)
class TestRuntimeException(message: String? = null, private val data: Any? = null) : RuntimeException(message)
class RecoverableTestException(message: String? = null) : RuntimeException(message)
class RecoverableTestCancellationException(message: String? = null) : CancellationException(message)

fun wrapperDispatcher(context: CoroutineContext): CoroutineContext {
    val dispatcher = context[ContinuationInterceptor] as CoroutineDispatcher
    return object : CoroutineDispatcher() {
        override fun isDispatchNeeded(context: CoroutineContext): Boolean =
            dispatcher.isDispatchNeeded(context)

        override fun dispatch(context: CoroutineContext, block: Runnable) =
            dispatcher.dispatch(context, block)
    }
}

suspend fun wrapperDispatcher(): CoroutineContext = wrapperDispatcher(coroutineContext)

class BadClass {
    override fun equals(other: Any?): Boolean = error("equals")
    override fun hashCode(): Int = error("hashCode")
    override fun toString(): String = error("toString")
}

class CapturingHandler :
    AbstractCoroutineContextElement(CoroutineExceptionHandler),
    CoroutineExceptionHandler {
    private var unhandled: MutableList<Throwable>? = mutableListOf()

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        unhandled!!.add(exception)
    }

    fun getExceptions(): List<Throwable> {
        return unhandled!!.also { unhandled = null }
    }

    fun getException(): Throwable {
        val size = unhandled!!.size
        assertTrue(size == 1, "Expected one unhandled exception, but have $size: $unhandled")
        return unhandled!![0].also { unhandled = null }
    }
}

internal suspend inline fun <reified E : Throwable> assertCallsExceptionHandlerWith(
    crossinline operation: suspend (CoroutineExceptionHandler) -> Unit
): E {
    contract {
        callsInPlace(operation, InvocationKind.EXACTLY_ONCE)
    }
    val handler = CapturingHandler()
    return withContext(handler) {
        operation(handler)
        handler.getException().let {
            assertTrue(it is E, it.toString())
            it
        }
    }
}
