package com.mirego.trikot.streams.reactive.promise

import com.mirego.trikot.streams.reactive.verify
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PromiseContinuationTests {

    @Test
    fun callingOnSuccessExecutesBlockOnSuccess() {
        val value = 22
        var actualValue: Int? = null

        val block: (Int) -> Unit = { actualValue = it }

        Promise.resolve(22)
            .onSuccess(block)

        assertEquals(value, actualValue)
    }

    @Test
    fun callingOnSuccessDoesNotExecuteBlockOnError() {
        val throwable = Throwable()
        var blockCalled = false

        Promise.reject<Int>(throwable)
            .onSuccess { blockCalled = true }
            .verify(
                value = null,
                error = throwable,
                completed = false
            )

        assertFalse(blockCalled)
    }

    @Test
    fun callingOnSuccessDoesNotAlterValueOnSuccess() {
        val value = 22

        Promise.resolve(value)
            .onSuccess { }
            .verify(
                value = value,
                error = null,
                completed = true
            )
    }

    @Test
    fun callingOnSuccessReturnCanResolveToNewPromiseOnSuccess() {
        Promise.resolve(22)
            .onSuccessReturn { Promise.resolve("The value is $it") }
            .verify(
                value = "The value is 22",
                error = null,
                completed = true
            )
    }

    @Test
    fun callingOnSuccessReturnCanRejectNewPromiseOnSuccess() {
        val error = Throwable()

        Promise.resolve(22)
            .onSuccessReturn { Promise.reject<Int>(error) }
            .verify(
                value = null,
                error = error,
                completed = false
            )
    }

    @Test
    fun callingOnSuccessReturnDoesNotExecuteBlockOnError() {
        val throwable = Throwable()
        var blockCalled = false

        Promise.reject<Int>(throwable)
            .onSuccessReturn {
                blockCalled = true
                Promise.resolve(22)
            }
            .verify(
                value = null,
                error = throwable,
                completed = false
            )

        assertFalse(blockCalled)
    }

    @Test
    fun callingOnErrorExecutesBlockOnError() {
        val error = Throwable()
        var actualError: Throwable? = null

        val block: (Throwable) -> Unit = { actualError = it }

        Promise.reject<String>(error)
            .onError(block)

        assertEquals(error, actualError)
    }

    @Test
    fun callingOnErrorDoesNotExecuteBlockOnSuccess() {
        val value = 22
        var actualError: Throwable? = null

        val block: (Throwable) -> Unit = { actualError = it }

        Promise.resolve(value)
            .onError(block)

        assertEquals(null, actualError)
    }

    @Test
    fun callingOnErrorDoesNotAlterErrorOnError() {
        val error = Throwable()

        Promise.reject<Int>(error)
            .onError { print("") }
            .verify(
                value = null,
                error = error,
                completed = false
            )
    }

    @Test
    fun callingOnErrorReturnCanResolveToNewPromiseOfSameTypeOnSuccess() {
        val error = Throwable("Fail!")

        Promise.reject<String>(error)
            .onErrorReturn { Promise.resolve("${it.message} Not!") }
            .verify(
                value = "${error.message} Not!",
                error = null,
                completed = true
            )
    }

    @Test
    fun callingOnErrorReturnCanRejectNewPromiseOfSameTypeOnError() {
        val error = Throwable("Fail!")
        val newError = Throwable("Fail again!")

        Promise.reject<Int>(error)
            .onErrorReturn { Promise.reject(newError) }
            .verify(
                value = null,
                error = newError,
                completed = false
            )
    }

    @Test
    fun callingOnErrorReturnDoesNotExecuteBlockOnSuccess() {
        val value = 22
        var blockCalled = false

        Promise.resolve(value)
            .onErrorReturn {
                blockCalled = true
                Promise.reject(Throwable())
            }
            .verify(
                value = value,
                error = null,
                completed = true
            )

        assertFalse(blockCalled)
    }

    @Test
    fun callingThenExecuteSuccessBlockOnSuccess() {
        var successBlockCalled = false
        var errorBlockCalled = false

        Promise.resolve(22)
            .then(
                onSuccess = {
                    successBlockCalled = true
                },
                onError = {
                    errorBlockCalled = true
                }
            )

        assertTrue(successBlockCalled)
        assertFalse(errorBlockCalled)
    }

    @Test
    fun callingThenExecuteErrorBlockOnError() {
        var successBlockCalled = false
        var errorBlockCalled = false

        Promise.reject<Int>(Throwable())
            .then(
                onSuccess = {
                    successBlockCalled = true
                },
                onError = {
                    errorBlockCalled = true
                }
            )

        assertFalse(successBlockCalled)
        assertTrue(errorBlockCalled)
    }

    @Test
    fun callingThenReturnAppliesNewPromiseOnSuccess() {
        val value = 22

        Promise.resolve(value)
            .thenReturn(
                onSuccess = {
                    Promise.resolve("Resolved $it")
                },
                onError = {
                    Promise.reject(it)
                }
            )
            .verify(
                value = "Resolved 22",
                error = null,
                completed = true
            )
    }

    @Test
    fun callingThenReturnAppliesNewPromiseOnError() {
        val throwable = Throwable("Oops!")

        Promise.reject<Int>(throwable)
            .thenReturn(
                onSuccess = {
                    Promise.resolve("Resolved $it")
                },
                onError = {
                    Promise.resolve("Resolved error ${it.message}")
                }
            )
            .verify(
                value = "Resolved error ${throwable.message}",
                error = null,
                completed = true
            )
    }

    @Test
    fun callingFinallyExecutesProvidedBlockOnSuccess() {
        var finallyCalled = false

        Promise.resolve(22)
            .finally { finallyCalled = true }

        assertTrue(finallyCalled)
    }

    @Test
    fun callingFinallyExecutesProvidedBlockOnError() {
        var finallyCalled = false

        Promise.reject<Int>(Throwable())
            .finally { finallyCalled = true }

        assertTrue(finallyCalled)
    }

    @Test
    fun finallyReturnSuppliesNewPromiseOnSuccess() {
        Promise.resolve(22)
            .finallyReturn { Promise.resolve("Finally!") }
            .verify(
                value = "Finally!",
                error = null,
                completed = true
            )
    }

    @Test
    fun finallyReturnSuppliesNewPromiseOnError() {
        Promise.reject<Int>(Throwable())
            .finallyReturn { Promise.resolve("Finally!") }
            .verify(
                value = "Finally!",
                error = null,
                completed = true
            )
    }
}
