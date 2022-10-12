package com.mirego.trikot.datasources

import kotlin.test.Test
import kotlin.test.assertEquals

class DataSourceStateTests {
    companion object {
        const val YAY = "yay"
        val NAY = Error("nay")
    }

    @Test
    fun toDataSourceState() {
        assertEquals(DataSourceState.dataLoaded(YAY), DataState.data<String, Error>(YAY).toDataSourceState())
        assertEquals(DataSourceState.withError(NAY), DataState.error<String, Error>(NAY).toDataSourceState())
        assertEquals(DataSourceState(false, YAY, NAY), DataState.error(NAY, YAY).toDataSourceState())
        assertEquals(DataSourceState.loading(), DataState.pending<String, Error>().toDataSourceState())
        assertEquals(DataSourceState(true, YAY, null), DataState.pending<String, Error>(YAY).toDataSourceState())
    }
}
