package com.trikot.metaviews.sample.viewmodels.home

import com.mirego.trikot.metaviews.lifecycle.ApplicationState
import com.mirego.trikot.metaviews.lifecycle.ApplicationStatePublisher
import com.mirego.trikot.metaviews.properties.MetaAction
import com.mirego.trikot.streams.reactive.PublishSubject
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.distinctUntilChanged
import com.mirego.trikot.streams.reactive.filter
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.streams.reactive.map
import com.mirego.trikot.streams.reactive.shared
import com.trikot.metaviews.sample.metaviews.MetaListItem
import com.trikot.metaviews.sample.metaviews.MutableHeaderListItem
import com.trikot.metaviews.sample.metaviews.MutableMetaLabelListItem
import com.trikot.metaviews.sample.metaviews.MutableMetaNavigableListItem
import com.trikot.metaviews.sample.navigation.Destination
import com.trikot.metaviews.sample.navigation.NavigationDelegate
import org.reactivestreams.Publisher

class HomeViewModelImpl(private val delegate: NavigationDelegate) :
    ListViewModel {

    private val applicationStatePublisher = ApplicationStatePublisher()

    private val backgroundCount = Publishers.publishSubject<Int>()
    private val backgroundStateCounter =
        applicationStatePublisher.distinctUntilChanged()
            .filter { it == ApplicationState.FOREGROUND }
            .incrementCounter(backgroundCount)
            .shared()

    override val items = listOf(
        MutableMetaNavigableListItem().also {
            it.title.text = "Views ->".just()
            it.onTap = MetaAction { delegate.navigateTo(Destination.VIEWS) }.just()
            it.title.onTap = it.onTap
        } as MetaListItem,

        MutableMetaNavigableListItem().also {
            it.title.text = "Labels ->".just()
            it.onTap = MetaAction { delegate.navigateTo(Destination.LABELS) }.just()
            it.title.onTap = it.onTap
        } as MetaListItem,

        MutableMetaNavigableListItem().also {
            it.title.text = "Buttons ->".just()
            it.onTap = MetaAction { delegate.navigateTo(Destination.BUTTONS) }.just()
            it.title.onTap = it.onTap
        } as MetaListItem,

        MutableMetaNavigableListItem().also {
            it.title.text = "Images ->".just()
            it.onTap = MetaAction { delegate.navigateTo(Destination.IMAGES) }.just()
            it.title.onTap = it.onTap
        } as MetaListItem,

        MutableMetaNavigableListItem().also {
            it.title.text = "Input text ->".just()
            it.onTap = MetaAction { delegate.navigateTo(Destination.INPUT_TEXT) }.just()
            it.title.onTap = it.onTap
        } as MetaListItem,

        MutableHeaderListItem("Application state"),

        MutableMetaLabelListItem().apply {
            label.text = backgroundStateCounter.map { "Went to foreground $it times" }
        }
    )
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
