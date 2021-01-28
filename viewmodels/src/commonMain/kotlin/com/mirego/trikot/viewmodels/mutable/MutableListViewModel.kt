package com.mirego.trikot.viewmodels.mutable

import com.mirego.trikot.viewmodels.ListItemViewModel
import com.mirego.trikot.viewmodels.ListViewModel
import com.mirego.trikot.viewmodels.factory.PropertyFactory
import org.reactivestreams.Publisher

open class MutableListViewModel<T : ListItemViewModel> : MutableViewModel(), ListViewModel<T> {
    override var elements: Publisher<List<T>> = PropertyFactory.create(listOf())
}
