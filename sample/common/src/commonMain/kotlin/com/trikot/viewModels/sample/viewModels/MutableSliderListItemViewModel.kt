package com.trikot.viewmodels.sample.viewmodels

import com.mirego.trikot.streams.reactive.map
import com.mirego.trikot.viewmodels.mutable.MutableLabelViewModel
import com.mirego.trikot.viewmodels.mutable.MutableSliderViewModel
import com.mirego.trikot.viewmodels.mutable.MutableViewModel

class MutableSliderListItemViewModel(
    override var comparableId: String = ""
) : SliderListItemViewModel, MutableViewModel() {
    override val slider = MutableSliderViewModel(initialValue = 2)
    override val valueLabel =
        MutableLabelViewModel().apply { text = slider.selectedValue.map { it.toString() } }

    override fun isTheSame(other: ListItemViewModel): Boolean {
        return false
    }
}
