package com.mirego.sample.viewmodels.showcase

import com.mirego.sample.resources.SampleImageResource
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDButtonViewModelImpl
import com.mirego.trikot.viewmodels.declarative.content.VMDImageContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import kotlinx.coroutines.CoroutineScope

abstract class ShowcaseViewModelImpl(coroutineScope: CoroutineScope) : ShowcaseViewModel, VMDViewModelImpl(coroutineScope) {
    override val closeButton: VMDButtonViewModelImpl<VMDImageContent> = VMDComponents.Button.withImage(SampleImageResource.ICON_CLOSE, coroutineScope)
}
