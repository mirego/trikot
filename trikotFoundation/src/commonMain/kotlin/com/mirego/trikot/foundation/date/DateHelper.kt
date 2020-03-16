package com.mirego.trikot.foundation.date

import kotlin.time.ExperimentalTime

@ExperimentalTime
object DateHelper {
    fun compare(initial: Date, other: Date): Int {
        val difference = initial.epoch - other.epoch

        if (difference == 0L) {
            return 0
        }

        return if (difference > 0L) 1 else -1
    }

    fun equals(date: Date, other: Any?): Boolean {
        if (other is Date) {
            return date.epoch == other.epoch
        }

        return false
    }
}
