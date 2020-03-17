package com.trikot.metaviews.sample.viewmodels.home

import com.mirego.trikot.metaviews.properties.Color
import com.mirego.trikot.metaviews.properties.MetaAction
import com.mirego.trikot.metaviews.properties.MetaInputType
import com.mirego.trikot.metaviews.properties.MetaSelector
import com.mirego.trikot.streams.reactive.just
import com.trikot.metaviews.sample.metaviews.MetaListItem
import com.trikot.metaviews.sample.metaviews.MutableHeaderListItem
import com.trikot.metaviews.sample.metaviews.MutableMetaInputTextListItem
import com.trikot.metaviews.sample.navigation.NavigationDelegate

class InputTextViewModel(navigationDelegate: NavigationDelegate) : ListViewModel {
    override val items: List<MetaListItem> = listOf(
        MutableHeaderListItem(".backgroundColor"),
        MutableMetaInputTextListItem().also {
            it.inputText.backgroundColor = MetaSelector(Color(255, 0, 0)).just()
        },
        MutableHeaderListItem(".alpha"),
        MutableMetaInputTextListItem().also {
            it.inputText.alpha = 0.5f.just()
        },
        MutableHeaderListItem(".hidden"),
        MutableMetaInputTextListItem().also {
            it.inputText.hidden = true.just()
        },
        MutableHeaderListItem(".onTap"),
        MutableMetaInputTextListItem().also {
            it.inputText.onTap = MetaAction { navigationDelegate.showAlert("Tapped $it") }.just()
        },
        MutableHeaderListItem(".placeholder"),
        MutableMetaInputTextListItem().also {
            it.inputText.placeholderText = "Placeholder text".just()
        },
        MutableHeaderListItem(".textColor"),
        MutableMetaInputTextListItem().also {
            it.inputText.textColor = Color(255, 0, 0).just()
        },
        MutableHeaderListItem(".inputType = password"),
        MutableMetaInputTextListItem().also {
            it.inputText.inputType = MetaInputType.PASSWORD.just()
        }
    )
}
