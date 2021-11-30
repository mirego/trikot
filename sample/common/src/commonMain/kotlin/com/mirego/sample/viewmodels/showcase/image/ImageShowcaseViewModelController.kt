package com.mirego.sample.viewmodels.showcase.image

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.controller.VMDViewModelController

class ImageShowcaseViewModelController(i18N: I18N) : VMDViewModelController<ImageShowcaseViewModel, ImageShowcaseNavigationDelegate>() {
    override val viewModel: ImageShowcaseViewModelImpl = ImageShowcaseViewModelImpl(i18N, cancellableManager)

    override fun onCreate() {
        super.onCreate()
        viewModel.closeButton.setAction { navigationDelegate?.close() }
    }
}
