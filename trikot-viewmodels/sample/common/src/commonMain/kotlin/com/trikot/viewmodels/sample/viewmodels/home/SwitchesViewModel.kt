package com.trikot.viewmodels.sample.viewmodels.home

import com.mirego.trikot.foundation.ref.weakAtomicReference
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.viewmodels.ListItemViewModel
import com.mirego.trikot.viewmodels.mutable.MutableListViewModel
import com.mirego.trikot.viewmodels.properties.ViewModelAction
import com.trikot.viewmodels.sample.navigation.NavigationDelegate
import com.trikot.viewmodels.sample.viewmodels.MutableHeaderListItemViewModel
import com.trikot.viewmodels.sample.viewmodels.MutableToggleSwitchListItemViewModel
import org.reactivestreams.Publisher

class SwitchesViewModel :
    MutableListViewModel<ListItemViewModel>() {
    var navigationDelegate: NavigationDelegate? by weakAtomicReference()

    private val mockUseCasePublisher1 = Publishers.behaviorSubject(true)
    private val mockUseCasePublisher2 = Publishers.behaviorSubject(false)
    override var elements: Publisher<List<ListItemViewModel>> = listOf<ListItemViewModel>(
        MutableHeaderListItemViewModel("Switch"),
        MutableToggleSwitchListItemViewModel().apply {
            toggleSwitch.checked = mockUseCasePublisher1
            toggleSwitch.toggleSwitchAction = ViewModelAction {
                mockUseCasePublisher1.value = mockUseCasePublisher1.value?.let { !it }
            }.just()
        },
        MutableHeaderListItemViewModel(".hidden"),
        MutableToggleSwitchListItemViewModel().apply {
            toggleSwitch.hidden = true.just()
        },
        MutableHeaderListItemViewModel(".!enabled"),
        MutableToggleSwitchListItemViewModel().apply {
            toggleSwitch.enabled = false.just()
        },
        MutableHeaderListItemViewModel(".alpha"),
        MutableToggleSwitchListItemViewModel().apply {
            toggleSwitch.alpha = 0.5f.just()
            toggleSwitch.checked = mockUseCasePublisher2
            toggleSwitch.toggleSwitchAction = ViewModelAction {
                mockUseCasePublisher2.value = mockUseCasePublisher2.value?.let { !it }
            }.just()
        }
    ).just()
}
