package com.mirego.trikot.foundation.date

import platform.Foundation.NSDate
import platform.Foundation.NSISO8601DateFormatter
import platform.Foundation.dateByAddingTimeInterval
import platform.Foundation.timeIntervalSince1970
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
actual class Date(val nsDate: NSDate) {
    actual val epoch: Long = (nsDate.timeIntervalSince1970 * 1000).toLong()

    actual fun toISO8601(): String {
        return NSISO8601DateFormatter().stringFromDate(nsDate)
    }

    actual operator fun plus(duration: Duration): Date {
        return Date(nsDate.dateByAddingTimeInterval(((duration.toLongMilliseconds() / 1000.0))))
    }

    actual companion object DateFactory {
        actual val now: Date
            get() {
                return Date(NSDate())
            }

        actual fun fromISO8601(isoDate: String): Date {
            return Date(
                NSISO8601DateFormatter().dateFromString(
                    isoDate
                ) ?: NSDate()
            )
        }
    }

    actual operator fun compareTo(other: Date): Int {
        return DateHelper.compare(this, other)
    }

    actual override fun equals(other: Any?): Boolean {
        return DateHelper.equals(this, other)
    }
}
