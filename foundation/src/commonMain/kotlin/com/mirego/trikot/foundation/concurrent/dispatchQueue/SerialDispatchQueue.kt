package com.mirego.trikot.foundation.concurrent.dispatchQueue

class SerialDispatchQueue : SequentialDispatchQueue(SynchronousDispatchQueue())
