package com.mirego.trikot.foundation.concurrent.duration

class Duration(val milliseconds: Long) {
    operator fun plus(duration: Duration): Duration {
        return ofMilliseconds(duration.milliseconds + milliseconds)
    }

    companion object DurationFactory {
        private const val MILLISECONDS_PER_SECOND = 1000
        private const val SECONDS_PER_MINUTE = 60
        private const val MINUTES_PER_HOUR = 60
        private const val HOUR_PER_DAY = 24

        fun ofMilliseconds(milliseconds: Long): Duration {
            return Duration(milliseconds)
        }

        fun ofSeconds(seconds: Long): Duration {
            return ofMilliseconds(seconds * MILLISECONDS_PER_SECOND)
        }

        fun ofMinutes(minutes: Long): Duration {
            return ofSeconds(minutes * SECONDS_PER_MINUTE)
        }

        fun ofHours(hours: Long): Duration {
            return ofMinutes(hours * MINUTES_PER_HOUR)
        }

        fun ofDays(days: Long): Duration {
            return ofHours(days * HOUR_PER_DAY)
        }
    }
}

fun Int.milliseconds(): Duration {
    return this.toLong().milliseconds()
}

fun Long.milliseconds(): Duration {
    return Duration.ofMilliseconds(this)
}

fun Int.seconds(): Duration {
    return this.toLong().seconds()
}

fun Long.seconds(): Duration {
    return Duration.ofSeconds(this)
}

fun Int.minutes(): Duration {
    return this.toLong().days()
}

fun Long.minutes(): Duration {
    return Duration.ofMinutes(this)
}

fun Int.hours(): Duration {
    return this.toLong().hours()
}

fun Long.hours(): Duration {
    return Duration.ofHours(this)
}

fun Int.days(): Duration {
    return this.toLong().days()
}

fun Long.days(): Duration {
    return Duration.ofDays(this)
}
