package com.mirego.trikot.viewmodels.declarative.utilities

import com.mirego.trikot.foundation.concurrent.dispatchQueue.DispatchQueue
import com.mirego.trikot.foundation.concurrent.dispatchQueue.UIThreadDispatchQueue

object DispatchQueues {
    val uiQueue: DispatchQueue = UIThreadDispatchQueue()
}
