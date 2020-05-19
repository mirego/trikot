package com.trikot.viewmodels.sample.viewmodels.home

import com.mirego.trikot.streams.reactive.just
import com.trikot.viewmodels.sample.viewmodels.MutableSliderListItemViewModel
import com.trikot.viewmodels.sample.navigation.NavigationDelegate
import com.trikot.viewmodels.sample.viewmodels.ListItemViewModel
import com.trikot.viewmodels.sample.viewmodels.MutableHeaderListItemViewModel

class SlidersViewModel(navigationDelegate: NavigationDelegate) : ListViewModel {
    override val items: List<ListItemViewModel> = listOf(
        MutableHeaderListItemViewModel("Slider"),
        MutableSliderListItemViewModel(),

        MutableHeaderListItemViewModel(".hidden"),
        MutableSliderListItemViewModel().apply {
            slider.hidden = true.just()
            valueLabel.hidden = true.just()
        },

        MutableHeaderListItemViewModel(".alpha"),
        MutableSliderListItemViewModel().apply {
            slider.alpha = 0.5f.just()
        }
    )
}
