package com.mirego.sample.factories

import com.mirego.sample.viewmodels.home.HomeViewModelController
import com.mirego.sample.viewmodels.showcase.progress.ProgressShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.text.TextShowcaseViewModelController
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.kword.KWord

class SampleViewModelControllerFactoryImpl : SampleViewModelControllerFactory {

    private val i18N: I18N = KWord

    override fun home(): HomeViewModelController {
        return HomeViewModelController(i18N)
    }

    override fun textShowcase(): TextShowcaseViewModelController {
        return TextShowcaseViewModelController(i18N)
    }

    override fun progressShowcase(): ProgressShowcaseViewModelController {
        return ProgressShowcaseViewModelController(i18N)
    }
}
