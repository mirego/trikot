package com.trikot.viewmodels.sample.viewmodels.home

import com.mirego.trikot.viewmodels.properties.Color
import com.mirego.trikot.viewmodels.properties.ViewModelAction
import com.mirego.trikot.viewmodels.properties.StateSelector
import com.mirego.trikot.streams.reactive.just
import com.trikot.viewmodels.sample.viewmodels.ListItemViewModel
import com.trikot.viewmodels.sample.viewmodels.MutableHeaderListItemViewModel
import com.trikot.viewmodels.sample.viewmodels.MutableViewListItemViewModel
import com.trikot.viewmodels.sample.navigation.NavigationDelegate

class ViewsViewModel(navigationDelegate: NavigationDelegate) : ListViewModel {
    override val items: List<ListItemViewModel> = listOf(
        MutableHeaderListItemViewModel(".backgroundColor"),
        MutableViewListItemViewModel().also {
            it.view.backgroundColor = StateSelector(Color(255, 0, 0)).just()
        },
        MutableHeaderListItemViewModel(".alpha"),
        MutableViewListItemViewModel().also {
            it.view.alpha = 0.5f.just()
        },
        MutableHeaderListItemViewModel(".hidden"),
        MutableViewListItemViewModel().also {
            it.view.hidden = true.just()
        },
        MutableHeaderListItemViewModel(".onTap"),
        MutableViewListItemViewModel().also {
            it.view.action = ViewModelAction { navigationDelegate.showAlert("Tapped $it") }.just()
        }
    )
}
