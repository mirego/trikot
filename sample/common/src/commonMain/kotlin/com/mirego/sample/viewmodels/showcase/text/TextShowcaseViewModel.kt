package com.mirego.sample.viewmodels.showcase.text

import com.mirego.sample.viewmodels.showcase.ShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel

interface TextShowcaseViewModel : ShowcaseViewModel {
    val text: VMDTextViewModel
}
