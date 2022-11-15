package com.mirego.sample.factories

import com.mirego.sample.viewmodels.home.HomeViewModelController
import com.mirego.sample.viewmodels.showcase.animation.types.AnimationTypesShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.components.button.ButtonShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.components.image.ImageShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.components.list.ListShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.components.picker.PickerShowcaseViewModelController
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

    override fun textShowcase() =
        TextShowcaseViewModelController(i18N)

    override fun progressShowcase() =
        ProgressShowcaseViewModelController(i18N)

    override fun imageShowcase() =
        ImageShowcaseViewModelController(i18N)

    override fun buttonShowcase() =
        ButtonShowcaseViewModelController(i18N)

    override fun toggleShowcase() =
        ToggleShowcaseViewModelController(i18N)

    override fun textFieldShowcase() =
        TextFieldShowcaseViewModelController(i18N)

    override fun animationTypesShowcase() =
        AnimationTypesShowcaseViewModelController(i18N)

    override fun listShowcase() =
        ListShowcaseViewModelController(i18N)

    override fun pickerShowcase() =
        PickerShowcaseViewModelController(i18N)
}
