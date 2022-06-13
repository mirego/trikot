package com.mirego.sample.viewmodels.showcase.components.toggle

import com.mirego.sample.viewmodels.showcase.ShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDToggleViewModel
import com.mirego.trikot.viewmodels.declarative.content.VMDImageContent
import com.mirego.trikot.viewmodels.declarative.content.VMDNoContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextImagePairContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextPairContent

interface ToggleShowcaseViewModel : ShowcaseViewModel {
    val checkboxTitle: VMDTextViewModel
    val textCheckboxTitle: VMDTextViewModel
    val imageCheckboxTitle: VMDTextViewModel
    val textImageCheckboxTitle: VMDTextViewModel
    val textPairCheckboxTitle: VMDTextViewModel
    val switchTitle: VMDTextViewModel
    val textSwitchTitle: VMDTextViewModel
    val imageSwitchTitle: VMDTextViewModel
    val textImageSwitchTitle: VMDTextViewModel
    val textPairSwitchTitle: VMDTextViewModel

    val emptyToggle: VMDToggleViewModel<VMDNoContent>
    val textToggle: VMDToggleViewModel<VMDTextContent>
    val imageToggle: VMDToggleViewModel<VMDImageContent>
    val textImageToggle: VMDToggleViewModel<VMDTextImagePairContent>
    val textPairToggle: VMDToggleViewModel<VMDTextPairContent>
}
