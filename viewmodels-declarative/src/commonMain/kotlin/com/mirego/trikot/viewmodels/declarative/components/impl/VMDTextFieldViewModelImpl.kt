package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.PublishSubjectImpl
import com.mirego.trikot.streams.reactive.first
import com.mirego.trikot.streams.reactive.observeOn
import com.mirego.trikot.streams.reactive.subscribe
import com.mirego.trikot.viewmodels.declarative.components.VMDTextFieldViewModel
import com.mirego.trikot.viewmodels.declarative.properties.VMDKeyboardAutoCapitalization
import com.mirego.trikot.viewmodels.declarative.properties.VMDKeyboardReturnKeyType
import com.mirego.trikot.viewmodels.declarative.properties.VMDKeyboardType
import com.mirego.trikot.viewmodels.declarative.properties.VMDTextContentType
import com.mirego.trikot.viewmodels.declarative.utilities.VMDDispatchQueues
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDPublishedProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.published
import org.reactivestreams.Publisher

@Suppress("LeakingThis")
open class VMDTextFieldViewModelImpl(cancellableManager: CancellableManager) :
    VMDControlViewModelImpl(cancellableManager), VMDTextFieldViewModel {

    private val textDelegate = published("", this)
    override var text: String by textDelegate

    private val placeholderDelegate = published("", this)
    override var placeholder: String by placeholderDelegate

    private val keyboardTypeDelegate = published(VMDKeyboardType.Default, this)
    override var keyboardType: VMDKeyboardType by keyboardTypeDelegate

    private val keyboardReturnKeyTypeDelegate = published(VMDKeyboardReturnKeyType.Default, this)
    override var keyboardReturnKeyType: VMDKeyboardReturnKeyType by keyboardReturnKeyTypeDelegate

    private val contentTypeDelegate = published(null as VMDTextContentType?, this)
    override var contentType: VMDTextContentType? by contentTypeDelegate

    private val autoCorrectDelegate = published(true, this)
    override var autoCorrect: Boolean by autoCorrectDelegate

    private val autoCapitalizationDelegate =
        published(VMDKeyboardAutoCapitalization.Sentences, this)
    override var autoCapitalization: VMDKeyboardAutoCapitalization by autoCapitalizationDelegate

    val onReturnKeyTapPublisher = PublishSubjectImpl<Unit>()
    override val onReturnKeyTap = {
        onReturnKeyTapPublisher.value = Unit
    }

    override var formatText: (text: String) -> String = { text -> text }

    override fun onValueChange(text: String) {
        this.text = text
    }

    fun addReturnKeyTapAction(action: () -> Unit) {
        onReturnKeyTapPublisher
            .observeOn(VMDDispatchQueues.uiQueue)
            .subscribe(
                cancellableManager,
                onNext = {
                    action()
                }
            )
    }

    fun <T> addReturnKeyTapAction(publisher: Publisher<T>, action: (T) -> Unit) {
        onReturnKeyTapPublisher
            .subscribe(
                cancellableManager,
                onNext = {
                    publisher
                        .first()
                        .observeOn(VMDDispatchQueues.uiQueue)
                        .subscribe(
                            cancellableManager,
                            onNext = { result ->
                                action(result)
                            }
                        )
                }
            )
    }

    fun bindPlaceholder(publisher: Publisher<String>) {
        updatePropertyPublisher(this::placeholder, cancellableManager, publisher)
    }

    fun bindKeyboardType(publisher: Publisher<VMDKeyboardType>) {
        updatePropertyPublisher(this::keyboardType, cancellableManager, publisher)
    }

    fun bindKeyboardReturnKeyType(publisher: Publisher<VMDKeyboardReturnKeyType>) {
        updatePropertyPublisher(this::keyboardReturnKeyType, cancellableManager, publisher)
    }

    fun bindContentType(publisher: Publisher<VMDTextContentType?>) {
        updatePropertyPublisher(this::contentType, cancellableManager, publisher)
    }

    fun bindAutoCorrect(publisher: Publisher<Boolean>) {
        updatePropertyPublisher(this::autoCorrect, cancellableManager, publisher)
    }

    fun bindAutoCapitalization(publisher: Publisher<VMDKeyboardAutoCapitalization>) {
        updatePropertyPublisher(this::autoCapitalization, cancellableManager, publisher)
    }

    override val propertyMapping: Map<String, VMDPublishedProperty<*>> by lazy {
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
