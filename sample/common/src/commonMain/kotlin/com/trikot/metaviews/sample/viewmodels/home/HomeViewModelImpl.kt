package com.trikot.metaviews.sample.viewmodels.home

import com.mirego.trikot.metaviews.properties.MetaAction
import com.mirego.trikot.streams.reactive.just
import com.trikot.metaviews.sample.metaviews.MetaListItem
import com.trikot.metaviews.sample.metaviews.MutableMetaNavigableListItem
import com.trikot.metaviews.sample.navigation.Destination
import com.trikot.metaviews.sample.navigation.NavigationDelegate

class HomeViewModelImpl(private val delegate: NavigationDelegate) :
    ListViewModel {
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
        } as MetaListItem
    )
}

