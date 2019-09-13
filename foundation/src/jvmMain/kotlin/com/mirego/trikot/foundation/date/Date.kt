package com.mirego.trikot.foundation.date

import org.threeten.bp.Instant
import java.text.SimpleDateFormat
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale
import java.util.TimeZone
import org.threeten.bp.OffsetDateTime.from
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

private val dateFormat =
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        .also { it.timeZone = TimeZone.getTimeZone("UTC") }

@ExperimentalTime
actual class Date(val instant: Instant) {
    actual val epoch: Long
        get() {
            return instant.toEpochMilli()
        }

    actual operator fun plus(duration: Duration): Date {
        return Date(Instant.ofEpochMilli(instant.toEpochMilli() + duration.toLongMilliseconds()))
    }

    actual operator fun compareTo(other: Date): Int {
        return (epoch - other.epoch).toInt()
    }

    actual fun toISO8601(): String {
        return dateFormat.format(java.util.Date(instant.toEpochMilli()))
    }

    actual companion object DateFactory {

        private val dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

        actual fun fromISO8601(isoDate: String): Date {
            return Date(dateTimeFormatter.parse(isoDate) { from(it) }.toInstant())
        }

        actual val now: Date get() = Date(Instant.now())
    }
}
