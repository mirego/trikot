package com.trikot.viewmodels.sample.viewmodels.home

import com.mirego.trikot.foundation.ref.weakAtomicReference
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.viewmodels.ListItemViewModel
import com.mirego.trikot.viewmodels.mutable.MutableListViewModel
import com.mirego.trikot.viewmodels.properties.Color
import com.mirego.trikot.viewmodels.properties.InputTextEditorAction
import com.mirego.trikot.viewmodels.properties.InputTextType
import com.mirego.trikot.viewmodels.properties.StateSelector
import com.mirego.trikot.viewmodels.properties.ViewModelAction
import com.trikot.viewmodels.sample.navigation.NavigationDelegate
import com.trikot.viewmodels.sample.viewmodels.MutableHeaderListItemViewModel
import com.trikot.viewmodels.sample.viewmodels.MutableInputTextListItemViewModel
import org.reactivestreams.Publisher

class InputTextViewModel : MutableListViewModel<ListItemViewModel>() {
    var navigationDelegate: NavigationDelegate? by weakAtomicReference()

    override var elements: Publisher<List<ListItemViewModel>> = listOf<ListItemViewModel>(
        MutableHeaderListItemViewModel(".backgroundColor"),
        MutableInputTextListItemViewModel().also {
            it.inputText.backgroundColor = StateSelector(Color(255, 0, 0)).just()
        },
        MutableHeaderListItemViewModel(".alpha"),
        MutableInputTextListItemViewModel().also {
            it.inputText.alpha = 0.5f.just()
        },
        MutableHeaderListItemViewModel(".hidden"),
        MutableInputTextListItemViewModel().also {
            it.inputText.hidden = true.just()
        },
        MutableHeaderListItemViewModel(".!enabled"),
        MutableInputTextListItemViewModel().also {
            it.inputText.enabled = false.just()
        },
        MutableHeaderListItemViewModel(".editorAction"),
        MutableInputTextListItemViewModel().also {
            it.inputText.editorAction = InputTextEditorAction {
                navigationDelegate?.showAlert("Editor action")
                true
            }.just()
        },
        MutableHeaderListItemViewModel(".onTap"),
        MutableInputTextListItemViewModel().also {
            it.inputText.action = ViewModelAction { navigationDelegate?.showAlert("Tapped $it") }.just()
        },
        MutableHeaderListItemViewModel(".placeholder"),
        MutableInputTextListItemViewModel().also {
            it.inputText.placeholderText = "Placeholder text".just()
        },
        MutableHeaderListItemViewModel(".textColor"),
        MutableInputTextListItemViewModel().also {
            it.inputText.textColor = Color(255, 0, 0).just()
        },
        MutableHeaderListItemViewModel(".inputType = password"),
        MutableInputTextListItemViewModel().also {
            it.inputText.inputType = InputTextType.PASSWORD.just()
        },
        MutableHeaderListItemViewModel(".inputType = number decimal"),
        MutableInputTextListItemViewModel().also {
            it.inputText.inputType = InputTextType.NUMBER_DECIMAL.just()
        }
    ).just()
}
