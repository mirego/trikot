package com.mirego.trikot.foundation.date

import com.mirego.trikot.foundation.system.OSVersion
import com.mirego.trikot.foundation.system.osVersionAtLeast
import platform.Foundation.NSDate
import platform.Foundation.NSISO8601DateFormatWithFractionalSeconds
import platform.Foundation.NSISO8601DateFormatWithInternetDateTime
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
        return Date(nsDate.dateByAddingTimeInterval(((duration.inWholeMilliseconds / 1000.0))))
    }

    actual companion object DateFactory {
        private const val epochReferenceDateDelta: Double = 978307200.0

        actual val now: Date
            get() {
                return Date(NSDate())
            }

        @ExperimentalUnsignedTypes
        actual fun fromISO8601(isoDate: String): Date {
            return when {
                osVersionAtLeast(OSVersion.iOS_11, OSVersion.macOS_10_13, OSVersion.tvOS_11, OSVersion.watchOS_4) ->
                    getFormatterWithCorrespondingFormatOptions(isoDate)
                        .dateFromString(isoDate)
                else ->
                    NSISO8601DateFormatter()
                        .dateFromString(removeFractionalSeconds(isoDate))
            }
                ?.let(::Date)
                ?: throw RuntimeException("Could not parse date $isoDate")
        }

        actual fun fromEpochMillis(epoch: Long): Date {
            return Date(NSDate(timeIntervalSinceReferenceDate = (epoch.toDouble() / 1000.0) - epochReferenceDateDelta))
        }

        @ExperimentalUnsignedTypes
        private fun getFormatterWithCorrespondingFormatOptions(date: String): NSISO8601DateFormatter =
            when {
                containsFractionalSeconds(date) -> NSISO8601DateFormatter().apply {
                    formatOptions = NSISO8601DateFormatWithInternetDateTime
                        .or(NSISO8601DateFormatWithFractionalSeconds)
                }
                else -> NSISO8601DateFormatter()
            }

        private fun removeFractionalSeconds(date: String): String =
            with(date) {
                when {
                    containsFractionalSeconds(this) -> substringBeforeLast(".") + "Z"
                    else -> this
                }
            }

        private fun containsFractionalSeconds(date: String): Boolean = date.lastIndexOf(".") != -1
    }

    actual operator fun compareTo(other: Date): Int {
        return DateHelper.compare(this, other)
    }

    actual override fun equals(other: Any?): Boolean {
        return DateHelper.equals(this, other)
    }
}
