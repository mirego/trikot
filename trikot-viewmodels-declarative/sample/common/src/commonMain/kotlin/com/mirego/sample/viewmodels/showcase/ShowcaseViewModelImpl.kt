package com.mirego.sample.viewmodels.showcase

import com.mirego.sample.resources.SampleImageResource
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDButtonViewModelImpl
import com.mirego.trikot.viewmodels.declarative.content.VMDImageContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl

abstract class ShowcaseViewModelImpl(cancellableManager: CancellableManager) : ShowcaseViewModel, VMDViewModelImpl(cancellableManager) {
    override val closeButton: VMDButtonViewModelImpl<VMDImageContent> = VMDComponents.Button.withImage(SampleImageResource.ICON_CLOSE, cancellableManager, contentDescription = "Close")
}
