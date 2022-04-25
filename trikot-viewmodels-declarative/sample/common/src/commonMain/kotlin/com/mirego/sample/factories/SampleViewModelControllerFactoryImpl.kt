package com.mirego.sample.factories

import com.mirego.sample.viewmodels.home.HomeViewModelController
import com.mirego.sample.viewmodels.showcase.animation.types.AnimationTypesShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.components.button.ButtonShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.components.image.ImageShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.components.progress.ProgressShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.components.text.TextShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.components.textfield.TextFieldShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.components.toggle.ToggleShowcaseViewModelController
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

    override fun animationTypesShowcase(): AnimationTypesShowcaseViewModelController {
        return AnimationTypesShowcaseViewModelController(i18N)
    }
}
