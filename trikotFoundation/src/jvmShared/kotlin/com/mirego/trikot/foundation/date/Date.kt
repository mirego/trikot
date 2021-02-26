package com.mirego.trikot.foundation.date

import org.threeten.bp.Instant
import org.threeten.bp.OffsetDateTime.from
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

private val dateFormat =
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        .withZone(ZoneOffset.UTC)

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
        return DateHelper.compare(this, other)
    }

    actual override fun equals(other: Any?): Boolean {
        return DateHelper.equals(this, other)
    }

    actual fun toISO8601(): String {
        return dateFormat.format(instant)
    }

    actual companion object DateFactory {
        actual val now: Date get() = Date(Instant.now())

        private val dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

        actual fun fromISO8601(isoDate: String): Date = Date(dateTimeFormatter.parse(isoDate) { from(it) }.toInstant())

        actual fun fromEpochMillis(epoch: Long): Date = Date(Instant.ofEpochMilli(epoch))
    }
}
