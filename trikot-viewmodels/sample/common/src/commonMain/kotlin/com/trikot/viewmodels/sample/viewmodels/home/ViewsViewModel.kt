package com.trikot.viewmodels.sample.viewmodels.home

import com.mirego.trikot.foundation.ref.weakAtomicReference
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.streams.reactive.map
import com.mirego.trikot.viewmodels.ListItemViewModel
import com.mirego.trikot.viewmodels.ViewModelAccessibilityHint
import com.mirego.trikot.viewmodels.mutable.MutableListViewModel
import com.mirego.trikot.viewmodels.properties.Color
import com.mirego.trikot.viewmodels.properties.StateSelector
import com.mirego.trikot.viewmodels.properties.ViewModelAction
import com.trikot.viewmodels.sample.navigation.NavigationDelegate
import com.trikot.viewmodels.sample.viewmodels.MutableHeaderListItemViewModel
import com.trikot.viewmodels.sample.viewmodels.MutableViewListItemViewModel
import org.reactivestreams.Publisher

class ViewsViewModel : MutableListViewModel<ListItemViewModel>() {

    var navigationDelegate: NavigationDelegate? by weakAtomicReference()

    private val hasPurchasedExample = Publishers.behaviorSubject(false)

    override var elements: Publisher<List<ListItemViewModel>> = listOf<ListItemViewModel>(
        MutableHeaderListItemViewModel(".backgroundColor"),
        MutableViewListItemViewModel().apply {
            view.backgroundColor = StateSelector(Color(255, 0, 0)).just()
        },
        MutableHeaderListItemViewModel(".alpha"),
        MutableViewListItemViewModel().apply {
            view.alpha = 0.5f.just()
        },
        MutableHeaderListItemViewModel(".hidden"),
        MutableViewListItemViewModel().apply {
            view.hidden = true.just()
        },
        MutableHeaderListItemViewModel(".onTap"),
        MutableViewListItemViewModel().apply {
            view.action = ViewModelAction { navigationDelegate?.showAlert("Tapped $it") }.just()
        },
        MutableHeaderListItemViewModel(".isAccessibilityElement (true)"),
        MutableViewListItemViewModel().apply {
            view.accessibilityLabel = "This should be accessible".just()
            view.isAccessibilityElement = true.just()
        },
        MutableHeaderListItemViewModel(".isAccessibilityElement (false)"),
        MutableViewListItemViewModel().apply {
            view.accessibilityLabel = "This should not be accessible".just()
            view.isAccessibilityElement = false.just()
        },
        MutableHeaderListItemViewModel(".accessibilityLabel (\"Sample view\")"),
        MutableViewListItemViewModel().apply {
            view.accessibilityLabel = "This is a sample view".just()
            view.isAccessibilityElement = true.just()
        },
        MutableHeaderListItemViewModel(".accessibilityHint (\"Purchase the item / Cancel your purchase\")"),
        MutableViewListItemViewModel().apply {
            view.isAccessibilityElement = true.just()
            view.accessibilityLabel = hasPurchasedExample.map { hasPurchased ->
                if (hasPurchased) "Cancel" else "Purchase"
            }
            view.accessibilityHint = hasPurchasedExample.map { hasPurchased ->
                when {
                    hasPurchased -> ViewModelAccessibilityHint(
                        hint = "Cancel your purchase",
                        customHintsChangeAnnouncement = "Interact again to cancel your purchase"
                    )
                    else -> ViewModelAccessibilityHint(hint = "Purchase the item")
                }
            }
            view.action = ViewModelAction {
                hasPurchasedExample.value = hasPurchasedExample.value?.let { !it } ?: false
            }.just()
        }
    ).just()
}
