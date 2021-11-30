package com.mirego.sample.viewmodels.showcase.text

import com.mirego.sample.viewmodels.showcase.ShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel

interface TextShowcaseViewModel : ShowcaseViewModel {
    val title1: VMDTextViewModel
    val title1Bold: VMDTextViewModel
    val title2: VMDTextViewModel
    val title2Bold: VMDTextViewModel
    val body: VMDTextViewModel
    val bodyMedium: VMDTextViewModel
}
