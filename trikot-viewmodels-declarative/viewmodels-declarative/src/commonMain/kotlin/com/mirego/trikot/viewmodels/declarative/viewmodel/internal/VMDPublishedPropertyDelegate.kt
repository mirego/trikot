package com.mirego.trikot.viewmodels.declarative.viewmodel.internal

fun <T> published(initialValue: T, listener: VMDPropertyChangeListener) =
    object : VMDPublishedProperty<T>(initialValue, listener) {}
