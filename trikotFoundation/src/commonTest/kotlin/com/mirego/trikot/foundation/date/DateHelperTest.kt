package com.mirego.trikot.foundation.date

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.minutes

@ExperimentalTime
class DateHelperTest {
    @Test
    fun testDateEquals() {
        val date = Date.now
        assertEquals(false, DateHelper.equals(date, null))
        assertEquals(false, DateHelper.equals(date, Date.now.plus(Duration.minutes(1))))
        assertEquals(true, DateHelper.equals(date, date))
    }

    @Test
    fun testDateCompare() {
        val date = Date.now
        assertEquals(0, DateHelper.compare(date, date))
        assertEquals(-1, DateHelper.compare(date, Date.now.plus(Duration.minutes(2))))
        assertEquals(1, DateHelper.compare(date, Date.now.plus(Duration.minutes(-10))))
    }

    @Test
    fun testDateFactory() {
        // TODO: Remove due to a bug in kotlin  1.3.70 (Invalid connection: com.apple.coresymbolicationd)
        // assertTrue(DateHelper.equals(Date.fromISO8601("2020-01-01T00:00:00Z"), Date.fromEpochMillis(1577836800000)))
    }
}
