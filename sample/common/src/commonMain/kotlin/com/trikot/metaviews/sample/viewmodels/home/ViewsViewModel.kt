package com.trikot.metaviews.sample.viewmodels.home

import com.mirego.trikot.metaviews.properties.Color
import com.mirego.trikot.metaviews.properties.MetaAction
import com.mirego.trikot.metaviews.properties.MetaSelector
import com.mirego.trikot.streams.reactive.just
import com.trikot.metaviews.sample.metaviews.MetaListItem
import com.trikot.metaviews.sample.metaviews.MutableHeaderListItem
import com.trikot.metaviews.sample.metaviews.MutableMetaViewListItem
import com.trikot.metaviews.sample.navigation.NavigationDelegate

class ViewsViewModel(navigationDelegate: NavigationDelegate) : ListViewModel {
    override val items: List<MetaListItem> = listOf(
        MutableHeaderListItem(".backgroundColor"),
        MutableMetaViewListItem().also {
            it.view.backgroundColor = MetaSelector(Color(255, 0, 0)).just()
        },
        MutableHeaderListItem(".alpha"),
        MutableMetaViewListItem().also {
            it.view.alpha = 0.5f.just()
        },
        MutableHeaderListItem(".hidden"),
        MutableMetaViewListItem().also {
            it.view.hidden = true.just()
        },
        MutableHeaderListItem(".onTap"),
        MutableMetaViewListItem().also {
            it.view.onTap = MetaAction { navigationDelegate.showAlert("Tapped $it") }.just()
        }
    )
}
