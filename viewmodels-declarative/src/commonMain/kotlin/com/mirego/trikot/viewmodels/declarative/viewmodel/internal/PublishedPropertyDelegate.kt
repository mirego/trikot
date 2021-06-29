package com.mirego.trikot.viewmodels.declarative.viewmodel.internal

fun <T> published(initialValue: T, listener: PropertyChangeListener) =
    object : PublishedProperty<T>(initialValue, listener) {}
