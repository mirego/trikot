package com.mirego.trikot.streams.reactive.promise

sealed class PromiseException : Exception()

object CancelledPromiseException : PromiseException()
object EmptyPromiseException : PromiseException()
