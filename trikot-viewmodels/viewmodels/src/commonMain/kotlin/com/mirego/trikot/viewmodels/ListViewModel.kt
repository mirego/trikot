package com.mirego.trikot.viewmodels

import org.reactivestreams.Publisher

interface ListViewModel<T : ListItemViewModel> : ViewModel {
    val elements: Publisher<List<T>>
}
