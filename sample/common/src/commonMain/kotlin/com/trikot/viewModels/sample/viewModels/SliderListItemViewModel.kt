package com.trikot.viewmodels.sample.viewmodels

import com.mirego.trikot.viewmodels.LabelViewModel
import com.mirego.trikot.viewmodels.ListItemViewModel
import com.mirego.trikot.viewmodels.SliderViewModel

interface SliderListItemViewModel : ListItemViewModel {
    val slider: SliderViewModel
    val valueLabel: LabelViewModel
}
