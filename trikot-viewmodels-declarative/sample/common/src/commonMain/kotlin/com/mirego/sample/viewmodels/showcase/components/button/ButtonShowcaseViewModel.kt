package com.mirego.sample.viewmodels.showcase.components.button

import com.mirego.sample.viewmodels.showcase.ShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDButtonViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.content.VMDImageContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextImagePairContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextPairContent

interface ButtonShowcaseViewModel : ShowcaseViewModel {
    val textButtonTitle: VMDTextViewModel
    val textButton: VMDButtonViewModel<VMDTextContent>

    val imageButtonTitle: VMDTextViewModel
    val imageButton: VMDButtonViewModel<VMDImageContent>

    val textImageButtonTitle: VMDTextViewModel
    val textImageButton: VMDButtonViewModel<VMDTextImagePairContent>

    val textPairButtonTitle: VMDTextViewModel
    val textPairButton: VMDButtonViewModel<VMDTextPairContent>
}
