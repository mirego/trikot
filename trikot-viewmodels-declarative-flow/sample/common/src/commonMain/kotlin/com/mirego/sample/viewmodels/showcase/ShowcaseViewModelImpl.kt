package com.mirego.sample.viewmodels.showcase

import com.mirego.sample.resources.SampleImageResource
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDButtonViewModelImpl
import com.mirego.trikot.viewmodels.declarative.content.VMDImageContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.buttonWithImage
import kotlinx.coroutines.CoroutineScope

abstract class ShowcaseViewModelImpl(coroutineScope: CoroutineScope) : ShowcaseViewModel, VMDViewModelImpl(coroutineScope) {
    override val closeButton: VMDButtonViewModelImpl<VMDImageContent> = buttonWithImage(SampleImageResource.ICON_CLOSE, contentDescription = "Close")
}
