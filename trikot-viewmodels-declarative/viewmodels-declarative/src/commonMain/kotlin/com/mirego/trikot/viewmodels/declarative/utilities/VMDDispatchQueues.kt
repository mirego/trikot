package com.mirego.trikot.viewmodels.declarative.utilities

import com.mirego.trikot.foundation.concurrent.dispatchQueue.TrikotDispatchQueue
import com.mirego.trikot.foundation.concurrent.dispatchQueue.UIThreadDispatchQueue

object VMDDispatchQueues {
    val uiQueue: TrikotDispatchQueue = UIThreadDispatchQueue()
}
