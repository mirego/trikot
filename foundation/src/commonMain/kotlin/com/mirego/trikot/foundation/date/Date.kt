package com.mirego.trikot.foundation.date

import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
expect class Date {
    val epoch: Long

    fun toISO8601(): String

    operator fun plus(duration: Duration): Date

    operator fun compareTo(other: Date): Int

    override fun equals(other: Any?): Boolean

    companion object DateFactory {
        val now: Date
        fun fromISO8601(isoDate: String): Date
        fun fromEpochMillis(epoch: Long): Date
    }
}
