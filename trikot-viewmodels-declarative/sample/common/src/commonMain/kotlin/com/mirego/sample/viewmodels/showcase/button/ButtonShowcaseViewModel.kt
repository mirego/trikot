package com.mirego.sample.viewmodels.showcase.button

import com.mirego.sample.viewmodels.showcase.ShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDButtonViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.content.VMDImageContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextImagePairContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextPairContent

interface ButtonShowcaseViewModel : ShowcaseViewModel {
    val textButtonTitle: VMDTextViewModel
    val imageButtonTitle: VMDTextViewModel
    val textImageButtonTitle: VMDTextViewModel
    val textPairButtonTitle: VMDTextViewModel
    val textButton: VMDButtonViewModel<VMDTextContent>
    val imageButton: VMDButtonViewModel<VMDImageContent>
    val textImageButton: VMDButtonViewModel<VMDTextImagePairContent>
    val textPairButton: VMDButtonViewModel<VMDTextPairContent>
}
