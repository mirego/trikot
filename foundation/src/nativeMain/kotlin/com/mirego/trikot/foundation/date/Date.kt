package com.mirego.trikot.foundation.date

import com.mirego.trikot.foundation.concurrent.duration.Duration
import platform.Foundation.NSDate
import platform.Foundation.NSISO8601DateFormatter
import platform.Foundation.dateByAddingTimeInterval
import platform.Foundation.timeIntervalSince1970

actual class Date(val nsDate: NSDate) {
    actual val epoch: Long = (nsDate.timeIntervalSince1970 * 1000).toLong()

    actual fun toISO8601(): String {
        return NSISO8601DateFormatter().stringFromDate(nsDate)
    }

    actual operator fun plus(duration: Duration): Date {
        return Date(nsDate.dateByAddingTimeInterval(((duration.milliseconds / 1000).toDouble())))
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
        val delta = epoch - other.epoch
        if (delta > 0) return 1
        if (delta < 0) return -1
        return 0
    }
}
