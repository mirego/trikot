package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.viewmodels.declarative.ViewModel
import com.mirego.trikot.viewmodels.declarative.properties.Content

interface ButtonViewModel<C : Content> : ViewModel {
    val content: C
    val action: () -> Unit
}
