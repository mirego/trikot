package com.mirego.sample.factories

import com.mirego.sample.viewmodels.home.HomeViewModelController
import com.mirego.sample.viewmodels.showcase.image.ImageShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.progress.ProgressShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.text.TextShowcaseViewModelController
import com.mirego.trikot.viewmodels.declarative.controller.VMDViewModelControllerFactory

interface SampleViewModelControllerFactory : VMDViewModelControllerFactory {
    fun home(): HomeViewModelController

    fun textShowcase(): TextShowcaseViewModelController

    fun progressShowcase(): ProgressShowcaseViewModelController

    fun imageShowcase(): ImageShowcaseViewModelController
}
