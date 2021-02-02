package com.trikot.viewmodels.sample.viewmodels.home

import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.viewmodels.ListItemViewModel
import com.mirego.trikot.viewmodels.mutable.MutableListViewModel
import com.mirego.trikot.viewmodels.properties.ViewModelAction
import com.trikot.viewmodels.sample.viewmodels.MutableSliderListItemViewModel
import com.trikot.viewmodels.sample.navigation.NavigationDelegate
import com.trikot.viewmodels.sample.viewmodels.MutableHeaderListItemViewModel
import com.trikot.viewmodels.sample.viewmodels.MutableSwitchListItemViewModel
import org.reactivestreams.Publisher

class SwitchesViewModel(navigationDelegate: NavigationDelegate) :
    MutableListViewModel<ListItemViewModel>() {
    override var elements: Publisher<List<ListItemViewModel>> = listOf<ListItemViewModel>(
        MutableHeaderListItemViewModel("Switch"),
        MutableSwitchListItemViewModel().apply {
            switch.action = ViewModelAction {
                println(switch.isOn.value)
            }.just()
        },

        MutableHeaderListItemViewModel(".hidden"),
        MutableSwitchListItemViewModel().apply {
            switch.hidden = true.just()
        },

        MutableHeaderListItemViewModel(".alpha"),
        MutableSwitchListItemViewModel().apply {
            switch.alpha = 0.5f.just()
            switch.action = ViewModelAction.None.just()
        }
    ).just()
}
