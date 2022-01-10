package com.trikot.viewmodels.sample.viewmodels

import com.mirego.trikot.viewmodels.mutable.ImageFlowProvider
import com.mirego.trikot.viewmodels.mutable.MutableImageViewModel
import com.mirego.trikot.viewmodels.mutable.MutableViewModel

class MutableImageListItemViewModel(imageFlowProvider: ImageFlowProvider, override var comparableId: String = "") : ImageListItemViewModel, MutableViewModel() {
    override val image = MutableImageViewModel(imageFlowProvider)
}
