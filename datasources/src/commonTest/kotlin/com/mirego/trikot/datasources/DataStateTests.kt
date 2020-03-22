package com.mirego.trikot.datasources

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class DataStateTests {

    @Test
    fun givenDataStateInPendingWithoutData() {
        val dataState: DataState<String, Error> = DataState.Pending()

        assertTrue { dataState.isPending() }
        assertTrue { dataState.isPending(false) }
        assertTrue { dataState.isPending(true) }
        assertTrue { !dataState.hasData() }
        assertTrue { !dataState.hasData(hasDataIfPending = false, hasDataIfError = false) }
        assertTrue { !dataState.hasData(hasDataIfPending = false, hasDataIfError = true) }
        assertTrue { !dataState.hasData(hasDataIfPending = true, hasDataIfError = false) }
        assertTrue { !dataState.hasData(hasDataIfPending = true, hasDataIfError = true) }
        assertTrue { !dataState.isError() }
        assertTrue { !dataState.isError(false) }
        assertTrue { !dataState.isError(true) }
    }

    @Test
    fun givenDataStateInPendingWithData() {
        val dataState: DataState<String, Error> = DataState.Pending("data")

        assertTrue { dataState.isPending() }
        assertTrue { !dataState.isPending(false) }
        assertTrue { dataState.isPending(true) }
        assertTrue { dataState.hasData() }
        assertTrue { !dataState.hasData(hasDataIfPending = false, hasDataIfError = false) }
        assertTrue { !dataState.hasData(hasDataIfPending = false, hasDataIfError = true) }
        assertTrue { dataState.hasData(hasDataIfPending = true, hasDataIfError = false) }
        assertTrue { dataState.hasData(hasDataIfPending = true, hasDataIfError = true) }
        assertTrue { !dataState.isError() }
        assertTrue { !dataState.isError(false) }
        assertTrue { !dataState.isError(true) }
    }

    @Test
    fun givenDataStateWithData() {
        val dataState: DataState<String, Error> = DataState.Data("data")

        assertTrue { !dataState.isPending() }
        assertTrue { !dataState.isPending(false) }
        assertTrue { !dataState.isPending(true) }
        assertTrue { dataState.hasData() }
        assertTrue { dataState.hasData(hasDataIfPending = false, hasDataIfError = false) }
        assertTrue { dataState.hasData(hasDataIfPending = false, hasDataIfError = true) }
        assertTrue { dataState.hasData(hasDataIfPending = true, hasDataIfError = false) }
        assertTrue { dataState.hasData(hasDataIfPending = true, hasDataIfError = true) }
        assertTrue { !dataState.isError() }
        assertTrue { !dataState.isError(false) }
        assertTrue { !dataState.isError(true) }
    }

    @Test
    fun givenDataStateInErrorWithoutData() {
        val dataState: DataState<String, Error> = DataState.Error(Error(), null)

        assertTrue { !dataState.isPending() }
        assertTrue { !dataState.isPending(false) }
        assertTrue { !dataState.isPending(true) }
        assertTrue { !dataState.hasData() }
        assertTrue { !dataState.hasData(hasDataIfPending = false, hasDataIfError = false) }
        assertTrue { !dataState.hasData(hasDataIfPending = false, hasDataIfError = true) }
        assertTrue { !dataState.hasData(hasDataIfPending = true, hasDataIfError = false) }
        assertTrue { !dataState.hasData(hasDataIfPending = true, hasDataIfError = true) }
        assertTrue { dataState.isError() }
        assertTrue { dataState.isError(false) }
        assertTrue { dataState.isError(true) }
    }

    @Test
    fun givenDataStateInErrorWithData() {
        val dataState: DataState<String, Error> = DataState.Error(Error(), "data")

        assertTrue { !dataState.isPending() }
        assertTrue { !dataState.isPending(false) }
        assertTrue { !dataState.isPending(true) }
        assertTrue { dataState.hasData() }
        assertTrue { !dataState.hasData(hasDataIfPending = false, hasDataIfError = false) }
        assertTrue { dataState.hasData(hasDataIfPending = false, hasDataIfError = true) }
        assertTrue { !dataState.hasData(hasDataIfPending = true, hasDataIfError = false) }
        assertTrue { dataState.hasData(hasDataIfPending = true, hasDataIfError = true) }
        assertTrue { dataState.isError() }
        assertTrue { !dataState.isError(false) }
        assertTrue { dataState.isError(true) }
    }

    @Test
    fun equalsAndHashcodeArePropertyImplemented() {
        val exception1 = Error()
        val exception2 = Error("Another")
        val pending1: DataState<String, Error> = DataState.Pending("value")
        val pending2: DataState<String, Error> = DataState.Pending("value")
        val pending3: DataState<String, Error> = DataState.Pending()
        val error1: DataState<String, Error> = DataState.Error(exception1, "value")
        val error2: DataState<String, Error> = DataState.Error(exception1, "value")
        val error3: DataState<String, Error> = DataState.Error(exception1)
        val error4: DataState<String, Error> = DataState.Error(exception2, "value")
        val data1: DataState<String, Error> = DataState.Data("value")
        val data2: DataState<String, Error> = DataState.Data("value")
        val data3: DataState<String, Error> = DataState.Data("other_value")

        assertEquals(pending1, pending2)
        assertNotEquals(pending1, pending3)
        assertEquals(error1, error2)
        assertNotEquals(error1, error3)
        assertNotEquals(error1, error4)
        assertEquals(data1, data2)
        assertNotEquals(data1, data3)
        assertNotEquals(pending1, data1)

        assertEquals(pending1.hashCode(), pending2.hashCode())
        assertNotEquals(pending1.hashCode(), pending3.hashCode())
        assertEquals(error1.hashCode(), error2.hashCode())
        assertNotEquals(error1.hashCode(), error3.hashCode())
        assertNotEquals(error1.hashCode(), error4.hashCode())
        assertEquals(data1.hashCode(), data2.hashCode())
        assertNotEquals(data1.hashCode(), data3.hashCode())
        // Fixed by disambiguatedHashCode
        assertNotEquals(pending1.hashCode(), data1.hashCode())
    }
}
