package com.mirego.trikot.foundation.date

import com.mirego.trikot.foundation.concurrent.duration.Duration
import com.mirego.trikot.foundation.concurrent.duration.milliseconds

expect class Date {
    val epoch: Long

    fun toISO8601(): String

    operator fun plus(duration: Duration): Date

    operator fun compareTo(other: Date): Int

    companion object DateFactory {
        val now: Date
        fun fromISO8601(isoDate: String): Date
    }
}
