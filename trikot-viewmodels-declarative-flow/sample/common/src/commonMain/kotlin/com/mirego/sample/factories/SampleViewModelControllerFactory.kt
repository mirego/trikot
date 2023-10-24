package com.mirego.sample.factories

import com.mirego.sample.viewmodels.home.HomeViewModelController
import com.mirego.sample.viewmodels.showcase.animation.types.AnimationTypesShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.components.button.ButtonShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.components.image.ImageShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.components.list.ListShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.components.picker.PickerShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.components.progress.ProgressShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.components.snackbar.SnackbarShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.components.text.TextShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.components.textfield.TextFieldShowcaseViewModelController
import com.mirego.sample.viewmodels.showcase.components.toggle.ToggleShowcaseViewModelController
import com.mirego.sample.viewmodels.tv.HomeTvViewModel
import com.mirego.sample.viewmodels.tv.HomeTvViewModelController
import com.mirego.trikot.viewmodels.declarative.controller.VMDViewModelControllerFactory

interface SampleViewModelControllerFactory : VMDViewModelControllerFactory {
    fun home(): HomeViewModelController

    fun textShowcase(): TextShowcaseViewModelController

    fun progressShowcase(): ProgressShowcaseViewModelController

    fun imageShowcase(): ImageShowcaseViewModelController

    fun buttonShowcase(): ButtonShowcaseViewModelController

    fun toggleShowcase(): ToggleShowcaseViewModelController

    fun textFieldShowcase(): TextFieldShowcaseViewModelController

    fun animationTypesShowcase(): AnimationTypesShowcaseViewModelController

    fun listShowcase(): ListShowcaseViewModelController

    fun pickerShowcase(): PickerShowcaseViewModelController

    fun snackbarShowcase(): SnackbarShowcaseViewModelController

    fun homeTv(): HomeTvViewModelController
}
