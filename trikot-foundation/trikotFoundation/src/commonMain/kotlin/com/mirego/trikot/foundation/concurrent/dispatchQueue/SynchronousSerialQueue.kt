package com.mirego.trikot.foundation.concurrent.dispatchQueue

class SynchronousSerialQueue : SequentialDispatchQueue(SynchronousDispatchQueue()) {
    override val isSynchronous = true
}
