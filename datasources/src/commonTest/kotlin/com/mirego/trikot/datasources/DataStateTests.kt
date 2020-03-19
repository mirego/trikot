package com.mirego.trikot.datasources

import kotlin.test.Test
import kotlin.test.assertTrue

class DataStateTests {

    @Test
    fun givenDataStateInPendingWithoutData() {
        val dataState: DataState<String, Error> = DataState.Pending()

        assertTrue { dataState.isPending() }
        assertTrue { dataState.isPending(true) }
        assertTrue { dataState.isPending(false) }
        assertTrue { !dataState.hasData() }
        assertTrue { !dataState.hasData(ignorePending = false, ignoreError = false) }
        assertTrue { !dataState.hasData(ignorePending = false, ignoreError = true) }
        assertTrue { !dataState.hasData(ignorePending = true, ignoreError = false) }
        assertTrue { !dataState.hasData(ignorePending = true, ignoreError = true) }
        assertTrue { !dataState.isError() }
        assertTrue { !dataState.isError(true) }
        assertTrue { !dataState.isError(false) }
    }

    @Test
    fun givenDataStateInPendingWithData() {
        val dataState: DataState<String, Error> = DataState.Pending("data")

        assertTrue { dataState.isPending() }
        assertTrue { dataState.isPending(true) }
        assertTrue { !dataState.isPending(false) }
        assertTrue { dataState.hasData() }
        assertTrue { !dataState.hasData(ignorePending = false, ignoreError = false) }
        assertTrue { !dataState.hasData(ignorePending = false, ignoreError = true) }
        assertTrue { dataState.hasData(ignorePending = true, ignoreError = false) }
        assertTrue { dataState.hasData(ignorePending = true, ignoreError = true) }
        assertTrue { !dataState.isError() }
        assertTrue { !dataState.isError(true) }
        assertTrue { !dataState.isError(false) }
    }

    @Test
    fun givenDataStateWithData() {
        val dataState: DataState<String, Error> = DataState.Data("data")

        assertTrue { !dataState.isPending() }
        assertTrue { !dataState.isPending(true) }
        assertTrue { !dataState.isPending(false) }
        assertTrue { dataState.hasData() }
        assertTrue { dataState.hasData(ignorePending = false, ignoreError = false) }
        assertTrue { dataState.hasData(ignorePending = false, ignoreError = true) }
        assertTrue { dataState.hasData(ignorePending = true, ignoreError = false) }
        assertTrue { dataState.hasData(ignorePending = true, ignoreError = true) }
        assertTrue { !dataState.isError() }
        assertTrue { !dataState.isError(true) }
        assertTrue { !dataState.isError(false) }
    }

    @Test
    fun givenDataStateInErrorWithoutData() {
        val dataState: DataState<String, Error> = DataState.Error(Error(), null)

        assertTrue { !dataState.isPending() }
        assertTrue { !dataState.isPending(true) }
        assertTrue { !dataState.isPending(false) }
        assertTrue { !dataState.hasData() }
        assertTrue { !dataState.hasData(ignorePending = false, ignoreError = false) }
        assertTrue { !dataState.hasData(ignorePending = false, ignoreError = true) }
        assertTrue { !dataState.hasData(ignorePending = true, ignoreError = false) }
        assertTrue { !dataState.hasData(ignorePending = true, ignoreError = true) }
        assertTrue { dataState.isError() }
        assertTrue { dataState.isError(true) }
        assertTrue { dataState.isError(false) }
    }

    @Test
    fun givenDataStateInErrorWithData() {
        val dataState: DataState<String, Error> = DataState.Error(Error(), "data")

        assertTrue { !dataState.isPending() }
        assertTrue { !dataState.isPending(true) }
        assertTrue { !dataState.isPending(false) }
        assertTrue { dataState.hasData() }
        assertTrue { !dataState.hasData(ignorePending = false, ignoreError = false) }
        assertTrue { dataState.hasData(ignorePending = false, ignoreError = true) }
        assertTrue { !dataState.hasData(ignorePending = true, ignoreError = false) }
        assertTrue { dataState.hasData(ignorePending = true, ignoreError = true) }
        assertTrue { dataState.isError() }
        assertTrue { dataState.isError(true) }
        assertTrue { !dataState.isError(false) }
    }
}
