package com.mirego.trikot.viewmodels.declarative.properties

import com.mirego.trikot.viewmodels.declarative.ViewModel

inline fun <T> published(initialValue: T, viewModel: ViewModel) = object : PublishedProperty<T>(initialValue, viewModel) {}
