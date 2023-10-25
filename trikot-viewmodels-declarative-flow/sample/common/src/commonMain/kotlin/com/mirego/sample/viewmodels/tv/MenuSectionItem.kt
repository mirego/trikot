package com.mirego.sample.viewmodels.tv

import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel

interface MenuSectionItem {
    val viewModel: VMDViewModel?
        get() = null
    val title: String
}

