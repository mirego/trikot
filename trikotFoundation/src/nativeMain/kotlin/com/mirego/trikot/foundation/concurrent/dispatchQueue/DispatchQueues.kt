package com.mirego.trikot.foundation.concurrent.dispatchQueue

actual class OperationDispatchQueue : iOSDispatchQueue(), DispatchQueue

actual class SerialSubscriptionDispatchQueue : iOSDispatchQueue(1), DispatchQueue
