package com.mirego.sample.viewmodels.showcase.text

import com.mirego.sample.viewmodels.showcase.ShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel

interface TextShowcaseViewModel : ShowcaseViewModel {
    val largeTitle: VMDTextViewModel
    val title1: VMDTextViewModel
    val title1Bold: VMDTextViewModel
    val title2: VMDTextViewModel
    val title2Bold: VMDTextViewModel
    val title3: VMDTextViewModel
    val headline: VMDTextViewModel
    val body: VMDTextViewModel
    val bodyMedium: VMDTextViewModel
    val button: VMDTextViewModel
    val callout: VMDTextViewModel
    val subheadline: VMDTextViewModel
    val footnote: VMDTextViewModel
    val caption1: VMDTextViewModel
    val caption2: VMDTextViewModel
}
