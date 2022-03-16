/*
 * Copyright 2016-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
 */

package kotlinx.coroutines.reactive

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import java.io.OutputStream
import java.io.PrintStream
import java.util.Collections
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.coroutineContext
import kotlin.test.assertTrue
import kotlin.test.expect

/**
 * Is `true` when running in a nightly stress test mode.
 */
actual val isStressTest = System.getProperty("stressTest")?.toBoolean() ?: false

actual val stressTestMultiplierSqrt = if (isStressTest) 5 else 1

private const val SHUTDOWN_TIMEOUT = 1_000L // 1s at most to wait per thread

actual val isNative = false

/**
 * Multiply various constants in stress tests by this factor, so that they run longer during nightly stress test.
 */
actual val stressTestMultiplier = stressTestMultiplierSqrt * stressTestMultiplierSqrt

@Suppress("ACTUAL_WITHOUT_EXPECT")
actual typealias TestResult = Unit

/**
 * Base class for tests, so that tests for predictable scheduling of actions in multiple coroutines sharing a single
 * thread can be written. Use it like this:
 *
 * ```
 * class MyTest : TestBase() {
 *    @Test
 *    fun testSomething() = runBlocking { // run in the context of the main thread
 *        expect(1) // initiate action counter
 *        launch { // use the context of the main thread
 *           expect(3) // the body of this coroutine in going to be executed in the 3rd step
 *        }
 *        expect(2) // launch just scheduled coroutine for execution later, so this line is executed second
 *        yield() // yield main thread to the launched job
 *        finish(4) // fourth step is the last one. `finish` must be invoked or test fails
 *    }
 * }
 * ```
 */
actual open class TestBase(private var disableOutCheck: Boolean) {

    actual constructor() : this(false)

    actual val isBoundByJsTestTimeout = false
    private var actionIndex = AtomicInteger()
    private var finished = AtomicBoolean()
    private var error = AtomicReference<Throwable>()

    // Shutdown sequence
    private lateinit var threadsBefore: Set<Thread>
    private val uncaughtExceptions = Collections.synchronizedList(ArrayList<Throwable>())
    private var originalUncaughtExceptionHandler: Thread.UncaughtExceptionHandler? = null

    /*
     * System.out that we redefine in order to catch any debugging/diagnostics
     * 'println' from main source set.
     * NB: We do rely on the name 'previousOut' in the FieldWalker in order to skip its
     * processing
     */
    private lateinit var previousOut: PrintStream

    /**
     * Throws [IllegalStateException] like `error` in stdlib, but also ensures that the test will not
     * complete successfully even if this exception is consumed somewhere in the test.
     */
    @Suppress("ACTUAL_FUNCTION_WITH_DEFAULT_ARGUMENTS")
    actual fun error(message: Any, cause: Throwable? = null): Nothing {
        throw makeError(message, cause)
    }

    fun hasError() = error.get() != null

    private fun makeError(message: Any, cause: Throwable? = null): IllegalStateException =
        IllegalStateException(message.toString(), cause).also {
            setError(it)
        }

    private fun setError(exception: Throwable) {
        error.compareAndSet(null, exception)
    }

    private fun printError(message: String, cause: Throwable) {
        setError(cause)
        println("$message: $cause")
        cause.printStackTrace(System.out)
        println("--- Detected at ---")
        Throwable().printStackTrace(System.out)
    }

    /**
     * Throws [IllegalStateException] when `value` is false like `check` in stdlib, but also ensures that the
     * test will not complete successfully even if this exception is consumed somewhere in the test.
     */
    inline fun check(value: Boolean, lazyMessage: () -> Any) {
        if (!value) error(lazyMessage())
    }

    /**
     * Asserts that this invocation is `index`-th in the execution sequence (counting from one).
     */
    actual fun expect(index: Int) {
        val wasIndex = actionIndex.incrementAndGet()
        check(index == wasIndex) { "Expecting action index $index but it is actually $wasIndex" }
    }

    /**
     * Asserts that this line is never executed.
     */
    actual fun expectUnreached() {
        error("Should not be reached, current action index is ${actionIndex.get()}")
    }

    /**
     * Asserts that this is the last action in the test. It must be invoked by any test that used [expect].
     */
    actual fun finish(index: Int) {
        expect(index)
        check(!finished.getAndSet(true)) { "Should call 'finish(...)' at most once" }
    }

    /**
     * Asserts that [finish] was invoked
     */
    actual fun ensureFinished() {
        require(finished.get()) { "finish(...) should be caller prior to this check" }
    }

    actual fun reset() {
        check(actionIndex.get() == 0 || finished.get()) { "Expecting that 'finish(...)' was invoked, but it was not" }
        actionIndex.set(0)
        finished.set(false)
    }

    private object TestOutputStream : PrintStream(object : OutputStream() {
        override fun write(b: Int) {
            error("Detected unexpected call to 'println' from source code")
        }
    })

    fun println(message: Any?) {
        if (disableOutCheck) kotlin.io.println(message)
        else previousOut.println(message)
    }

    @Before
    fun before() {
//        initPoolsBeforeTest()
//        threadsBefore = currentThreads()
        originalUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            println("Exception in thread $t: $e") // The same message as in default handler
            e.printStackTrace()
            uncaughtExceptions.add(e)
        }
        if (!disableOutCheck) {
            previousOut = System.out
            System.setOut(TestOutputStream)
        }
    }

    @After
    fun onCompletion() {
        // onCompletion should not throw exceptions before it finishes all cleanup, so that other tests always
        // start in a clear, restored state
        if (actionIndex.get() != 0 && !finished.get()) {
            makeError("Expecting that 'finish(${actionIndex.get() + 1})' was invoked, but it was not")
        }
//        // Shutdown all thread pools
//        shutdownPoolsAfterTest()
//        // Check that are now leftover threads
//        runCatching {
//            checkTestThreads(threadsBefore)
//        }.onFailure {
//            setError(it)
//        }
        // Restore original uncaught exception handler
        Thread.setDefaultUncaughtExceptionHandler(originalUncaughtExceptionHandler)
        if (!disableOutCheck) {
            System.setOut(previousOut)
        }
        if (uncaughtExceptions.isNotEmpty()) {
            makeError("Expected no uncaught exceptions, but got $uncaughtExceptions")
        }
        // The very last action -- throw error if any was detected
        error.get()?.let { throw it }
    }

    @Suppress("ACTUAL_WITHOUT_EXPECT", "ACTUAL_FUNCTION_WITH_DEFAULT_ARGUMENTS")
    actual fun runTest(
        expected: ((Throwable) -> Boolean)? = null,
        unhandled: List<(Throwable) -> Boolean> = emptyList(),
        block: suspend CoroutineScope.() -> Unit
    ): TestResult {
        var exCount = 0
        var ex: Throwable? = null
        try {
            runBlocking(
                block = block,
                context = CoroutineExceptionHandler { _, e ->
                    if (e is CancellationException) return@CoroutineExceptionHandler // are ignored
                    exCount++
                    when {
                        exCount > unhandled.size ->
                            printError("Too many unhandled exceptions $exCount, expected ${unhandled.size}, got: $e", e)
                        !unhandled[exCount - 1](e) ->
                            printError("Unhandled exception was unexpected: $e", e)
                    }
                }
            )
        } catch (e: Throwable) {
            ex = e
            if (expected != null) {
                if (!expected(e))
                    error("Unexpected exception: $e", e)
            } else
                throw e
        } finally {
            if (ex == null && expected != null) error("Exception was expected but none produced")
        }
        if (exCount < unhandled.size)
            error("Too few unhandled exceptions $exCount, expected ${unhandled.size}")
    }

    protected inline fun <reified T : Throwable> assertFailsWith(block: () -> Unit): T {
        val result = runCatching(block)
        assertTrue(result.exceptionOrNull() is T, "Expected ${T::class}, but had $result")
        return result.exceptionOrNull()!! as T
    }

    protected suspend fun currentDispatcher() = coroutineContext[ContinuationInterceptor]!!
}
