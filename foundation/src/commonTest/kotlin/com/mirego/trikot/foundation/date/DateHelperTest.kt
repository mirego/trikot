package com.mirego.trikot.foundation.date

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime
import kotlin.time.minutes

@ExperimentalTime
class DateHelperTest {
    @Test
    fun testDateEquals() {
        val date = Date.now
        assertEquals(false, DateHelper.equals(date, null))
        assertEquals(false, DateHelper.equals(date, Date.now.plus(1.minutes)))
        assertEquals(true, DateHelper.equals(date, date))
    }

    @Test
    fun testDateCompare() {
        val date = Date.now
        assertEquals(0, DateHelper.compare(date, date))
        assertEquals(-1, DateHelper.compare(date, Date.now.plus(2.minutes)))
        assertEquals(1, DateHelper.compare(date, Date.now.plus((-10).minutes)))
    }
}
