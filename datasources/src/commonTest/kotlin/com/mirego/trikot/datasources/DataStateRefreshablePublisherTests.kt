package com.mirego.trikot.datasources

import com.mirego.trikot.datasources.testutils.assertPending
import com.mirego.trikot.streams.reactive.Publishers
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class DataStateRefreshablePublisherTests {
    @Test
    fun givenDataWhenRefreshingThenPendingWithData() {
        val initialValue = "initial"

        val refreshable = DataStateRefreshablePublisher({ _, _ ->
            Publishers.behaviorSubject()
        }, DataState.data(initialValue))

        refreshable.refresh()
        assertNotNull(refreshable.value).assertPending(initialValue)
    }

    @Test
    fun givenErrorWithDataWhenRefreshingThenPendingWithData() {
        val initialValue = "initial"

        val refreshable = DataStateRefreshablePublisher({ _, _ ->
            Publishers.behaviorSubject()
        }, DataState.error(Throwable(), initialValue))

        refreshable.refresh()
        assertNotNull(refreshable.value).assertPending(initialValue)
    }

    @Test
    fun givenPendingWithDataWhenRefreshingThenPendingWithData() {
        val initialValue = "initial"

        val refreshable = DataStateRefreshablePublisher({ _, _ ->
            Publishers.behaviorSubject()
        }, DataState.pending(initialValue))

        refreshable.refresh()
        assertNotNull(refreshable.value).assertPending(initialValue)
    }

    @Test
    fun givenPendingWithoutDataWhenRefreshingThenPendingWithoutData() {
        val refreshable = DataStateRefreshablePublisher({ _, _ ->
            Publishers.behaviorSubject()
        }, DataState.Pending<String, Throwable>())

        refreshable.refresh()
        assertNotNull(refreshable.value).assertPending(null)
    }

    @Test
    fun givenNoInitialValueWhenRefreshingThenValueIsNull() {
        val refreshable = DataStateRefreshablePublisher({ _, _ ->
            Publishers.behaviorSubject<DataState<String, Throwable>>()
        })

        refreshable.refresh()
        assertNull(refreshable.value)
    }
}
