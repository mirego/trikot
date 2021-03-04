package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.viewmodels.declarative.ViewModel
import com.mirego.trikot.viewmodels.declarative.properties.IdentifiableContent

interface ListViewModel<C : IdentifiableContent> : ViewModel {
    val elements: List<C>
}
