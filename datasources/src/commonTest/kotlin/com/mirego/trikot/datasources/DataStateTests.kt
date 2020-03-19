package com.mirego.trikot.datasources

import kotlin.test.Test
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
}
