package com.trikot.viewmodels.sample.viewmodels.home

import com.mirego.trikot.streams.reactive.PublishSubject
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.distinctUntilChanged
import com.mirego.trikot.streams.reactive.filter
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.streams.reactive.map
import com.mirego.trikot.streams.reactive.shared
import com.mirego.trikot.viewmodels.ListItemViewModel
import com.mirego.trikot.viewmodels.lifecycle.ApplicationState
import com.mirego.trikot.viewmodels.lifecycle.ApplicationStatePublisher
import com.mirego.trikot.viewmodels.mutable.MutableListViewModel
import com.mirego.trikot.viewmodels.properties.ViewModelAction
import com.trikot.viewmodels.sample.navigation.Destination
import com.trikot.viewmodels.sample.navigation.NavigationDelegate
import com.trikot.viewmodels.sample.viewmodels.MutableHeaderListItemViewModel
import com.trikot.viewmodels.sample.viewmodels.MutableLabelListItemViewModel
import com.trikot.viewmodels.sample.viewmodels.MutableNavigableListItemViewModel
import org.reactivestreams.Publisher

class HomeViewModelImpl(private val delegate: NavigationDelegate) :
    MutableListViewModel<ListItemViewModel>() {

    private val applicationStatePublisher = ApplicationStatePublisher()

    private val backgroundCount = Publishers.publishSubject<Int>()
    private val backgroundStateCounter =
        applicationStatePublisher.distinctUntilChanged()
            .filter { it == ApplicationState.FOREGROUND }
            .incrementCounter(backgroundCount)
            .shared()

    override var elements: Publisher<List<ListItemViewModel>> = listOf<ListItemViewModel>(
        MutableNavigableListItemViewModel().also {
            it.title.text = "Views ->".just()
            it.action = ViewModelAction { delegate.navigateTo(Destination.VIEWS) }.just()
            it.title.action = it.action
        },

        MutableNavigableListItemViewModel().also {
            it.title.text = "Labels ->".just()
            it.action = ViewModelAction { delegate.navigateTo(Destination.LABELS) }.just()
            it.title.action = it.action
        },

        MutableNavigableListItemViewModel().also {
            it.title.text = "Buttons ->".just()
            it.action = ViewModelAction { delegate.navigateTo(Destination.BUTTONS) }.just()
            it.title.action = it.action
        },

        MutableNavigableListItemViewModel().also {
            it.title.text = "Images ->".just()
            it.action = ViewModelAction { delegate.navigateTo(Destination.IMAGES) }.just()
            it.title.action = it.action
        },

        MutableNavigableListItemViewModel().also {
            it.title.text = "Input text ->".just()
            it.action = ViewModelAction { delegate.navigateTo(Destination.INPUT_TEXT) }.just()
            it.title.action = it.action
        },

        MutableNavigableListItemViewModel().also {
            it.title.text = "Slider ->".just()
            it.action = ViewModelAction { delegate.navigateTo(Destination.SLIDERS) }.just()
            it.title.action = it.action
        },

        MutableNavigableListItemViewModel().also {
            it.title.text = "Switch ->".just()
            it.action = ViewModelAction { delegate.navigateTo(Destination.SWITCHES) }.just()
            it.title.action = it.action
        },

        MutableNavigableListItemViewModel().also {
            it.title.text = "Picker ->".just()
            it.action = ViewModelAction { delegate.navigateTo(Destination.PICKERS) }.just()
            it.title.action = it.action
        },

        MutableHeaderListItemViewModel("Application state"),

        MutableLabelListItemViewModel().apply {
            label.text = backgroundStateCounter.map { "Went to foreground $it times" }
        }
    ).just()
}

fun <T> Publisher<T>.incrementCounter(
    counter: PublishSubject<Int>
): Publisher<Int> {
    return map {
        val currentCount = counter.value ?: 0
        val nextCount = currentCount + 1
        counter.value = nextCount
        nextCount
    }
}
