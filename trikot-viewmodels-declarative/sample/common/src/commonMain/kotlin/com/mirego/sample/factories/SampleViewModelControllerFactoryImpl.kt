package com.mirego.sample.factories

import com.mirego.sample.viewmodels.home.HomeViewModelController
import com.mirego.sample.viewmodels.showcase.button.ButtonShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.image.ImageShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.progress.ProgressShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.text.TextShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.textfield.TextFieldShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.toggle.ToggleShowcaseViewModelController
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

    override fun imageShowcase(): ImageShowcaseViewModelController {
        return ImageShowcaseViewModelController(i18N)
    }

    override fun buttonShowcase(): ButtonShowcaseViewModelController {
        return ButtonShowcaseViewModelController(i18N)
    }

    override fun toggleShowcase(): ToggleShowcaseViewModelController {
        return ToggleShowcaseViewModelController(i18N)
    }

    override fun textFieldShowcase(): TextFieldShowcaseViewModelController {
        return TextFieldShowcaseViewModelController(i18N)
    }
}
