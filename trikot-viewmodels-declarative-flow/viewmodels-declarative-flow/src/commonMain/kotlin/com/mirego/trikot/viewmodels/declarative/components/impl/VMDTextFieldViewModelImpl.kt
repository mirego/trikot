package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.viewmodels.declarative.components.VMDTextFieldViewModel
import com.mirego.trikot.viewmodels.declarative.properties.VMDKeyboardAutoCapitalization
import com.mirego.trikot.viewmodels.declarative.properties.VMDKeyboardReturnKeyType
import com.mirego.trikot.viewmodels.declarative.properties.VMDKeyboardType
import com.mirego.trikot.viewmodels.declarative.properties.VMDTextContentType
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDFlowProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.emit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Suppress("LeakingThis")
open class VMDTextFieldViewModelImpl(coroutineScope: CoroutineScope) :
    VMDControlViewModelImpl(coroutineScope), VMDTextFieldViewModel {

    private val textDelegate = emit("", this, coroutineScope)
    override var text: String by textDelegate

    private val placeholderDelegate = emit("", this, coroutineScope)
    override var placeholder: String by placeholderDelegate

    private val keyboardTypeDelegate = emit(VMDKeyboardType.Default, this, coroutineScope)
    override var keyboardType: VMDKeyboardType by keyboardTypeDelegate

    private val keyboardReturnKeyTypeDelegate = emit(VMDKeyboardReturnKeyType.Default, this, coroutineScope)
    override var keyboardReturnKeyType: VMDKeyboardReturnKeyType by keyboardReturnKeyTypeDelegate

    private val contentTypeDelegate = emit(null as VMDTextContentType?, this, coroutineScope)
    override var contentType: VMDTextContentType? by contentTypeDelegate

    private val autoCorrectDelegate = emit(true, this, coroutineScope)
    override var autoCorrect: Boolean by autoCorrectDelegate

    private val autoCapitalizationDelegate =
        emit(VMDKeyboardAutoCapitalization.Sentences, this, coroutineScope)
    override var autoCapitalization: VMDKeyboardAutoCapitalization by autoCapitalizationDelegate

    val onReturnKeyTapStateFlow = MutableStateFlow(Unit)
    override val onReturnKeyTap = {
        onReturnKeyTapStateFlow.value = Unit
    }

    override var formatText: (text: String) -> String = { text -> text }
    override var unformatText: (text: String) -> String = { text -> text }
    override var transformText: (text: String) -> String = { text -> text }

    override fun onValueChange(text: String) {
        this.text = transformText(text)
    }

    fun addReturnKeyTapAction(action: () -> Unit) {
        coroutineScope.launch {
            onReturnKeyTapStateFlow
                .collect { action() }
        }
    }

    fun <T> addReturnKeyTapAction(flow: Flow<T>, action: (T) -> Unit) {
        coroutineScope.launch {
            onReturnKeyTapStateFlow
                .collect {
                    flow.collect { action(it) }
                }
        }
    }

    fun bindPlaceholder(flow: Flow<String>) {
        updateProperty(this::placeholder, flow)
    }

    fun bindKeyboardType(flow: Flow<VMDKeyboardType>) {
        updateProperty(this::keyboardType, flow)
    }

    fun bindKeyboardReturnKeyType(flow: Flow<VMDKeyboardReturnKeyType>) {
        updateProperty(this::keyboardReturnKeyType, flow)
    }

    fun bindContentType(flow: Flow<VMDTextContentType?>) {
        updateProperty(this::contentType, flow)
    }

    fun bindAutoCorrect(flow: Flow<Boolean>) {
        updateProperty(this::autoCorrect, flow)
    }

    fun bindAutoCapitalization(flow: Flow<VMDKeyboardAutoCapitalization>) {
        updateProperty(this::autoCapitalization, flow)
    }

    override val propertyMapping: Map<String, VMDFlowProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::text.name] = textDelegate
            it[this::placeholder.name] = placeholderDelegate
            it[this::keyboardType.name] = keyboardTypeDelegate
            it[this::keyboardReturnKeyType.name] = keyboardReturnKeyTypeDelegate
            it[this::contentType.name] = contentTypeDelegate
            it[this::autoCorrect.name] = autoCorrectDelegate
            it[this::autoCapitalization.name] = autoCapitalizationDelegate
        }
    }
}
