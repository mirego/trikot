import com.mirego.trikot.foundation.timers.Timer
import com.mirego.trikot.foundation.timers.TimerFactory
import kotlin.test.assertTrue
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

data class TimerData(val timer: MockTimer, val fireOffTime: Duration, val repeatTime: Duration = fireOffTime, val repeatable: Boolean = false) {
    var fired = false
}

class ConfigurableMockTimerFactory(initialTime: Duration = 0.toDuration(DurationUnit.MILLISECONDS)) : TimerFactory {
    var singleCall = 0
    var repeatableCall = 0
    val timers = mutableListOf<TimerData>()
    private var currentTime = initialTime

    fun addToTime(timeToAdd: Duration) = setTime(currentTime + timeToAdd)

    fun setTime(toTime: Duration) {
        if (toTime == currentTime) return
        assertTrue(toTime > currentTime)
        currentTime = toTime
        do {
            val initialTimersSize = timers.size
            timers
                .filterNot { it.fired }
                .forEach {
                    val expired = it.fireOffTime <= toTime
                    if (expired) {
                        it.timer.executeBlock()
                        it.fired = true
                        if (it.repeatable) {
                            timers.add(TimerData(it.timer, it.fireOffTime + it.repeatTime, it.repeatTime, repeatable = true))
                        }
                    }
                }
        } while (timers.size != initialTimersSize)
    }

    override fun repeatable(delay: Duration, block: () -> Unit): Timer {
        repeatableCall++

        return MockTimer(block).also {
            timers.add(TimerData(it, delay + currentTime, delay, repeatable = true))
        }
    }

    override fun single(delay: Duration, block: () -> Unit): Timer {
        singleCall++

        return MockTimer(block).also {
            timers.add(TimerData(it, delay + currentTime))
        }
    }
}

class MockTimer(private val block: () -> Unit) : Timer {
    var isCancelled = false
        private set

    fun executeBlock() {
        if (!isCancelled) {
            block.invoke()
        }
    }

    override fun cancel() {
        isCancelled = true
    }
}
