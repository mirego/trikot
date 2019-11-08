package com.mirego.trikot.foundation.date

import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
actual class Date(val date: kotlin.js.Date) {
    actual val epoch: Long = date.getTime().toLong()

    actual fun toISO8601(): String {
        return "${date.toISOString().split(".")[0]}Z"
    }

    actual operator fun plus(duration: Duration): Date {
        return Date(kotlin.js.Date(date.getTime() + duration.toLongMilliseconds()))
    }

    actual operator fun compareTo(other: Date): Int {
        val difference = epoch - other.epoch

        if (difference == 0L) {
            return 0
        }

        return if (difference > 0L) 1 else -1
    }

    actual override fun equals(other: Any?): Boolean {
        if (other is Date) {
            return epoch == other.epoch
        }

        return false
    }

    actual companion object DateFactory {
        // JS date are localized by default
        // going through `.toISOString()` make sure we deal with UTC
        actual val now: Date get() = fromISO8601(kotlin.js.Date().toISOString())

        actual fun fromISO8601(isoDate: String): Date {
            return Date(
                kotlin.js.Date(
                    kotlin.js.Date.parse(
                        isoDate
                    )
                )
            )
        }
    }
}
