package com.mirego.sample.viewmodels.home

import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.text
import kotlinx.coroutines.CoroutineScope

class HomeSectionViewModelImpl(
    text: String,
    override val elements: List<HomeSectionElementViewModel>,
    coroutineScope: CoroutineScope
) : VMDViewModelImpl(coroutineScope), HomeSectionViewModel {
    override val identifier = text
    override val title = text(text)
}
