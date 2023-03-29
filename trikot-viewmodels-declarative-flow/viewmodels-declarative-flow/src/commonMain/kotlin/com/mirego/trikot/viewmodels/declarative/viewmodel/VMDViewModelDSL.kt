package com.mirego.trikot.viewmodels.declarative.viewmodel

import com.mirego.trikot.viewmodels.declarative.components.VMDPickerItemViewModel
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDButtonViewModelImpl
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDImageViewModelImpl
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDListViewModelImpl
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDLoadingViewModelImpl
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDPickerViewModelImpl
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDProgressViewModelImpl
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDTextFieldViewModelImpl
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDTextViewModelImpl
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDToggleViewModelImpl
import com.mirego.trikot.viewmodels.declarative.content.VMDIdentifiableContent
import com.mirego.trikot.viewmodels.declarative.content.VMDImageContent
import com.mirego.trikot.viewmodels.declarative.content.VMDImageDescriptorContent
import com.mirego.trikot.viewmodels.declarative.content.VMDNoContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextImagePairContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextPairContent
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageDescriptor
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageResource
import com.mirego.trikot.viewmodels.declarative.properties.VMDRichTextSpan
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface VMDViewModelDSL {
    val coroutineScope: CoroutineScope
}

fun VMDViewModelDSL.text(content: String = "", spans: List<VMDRichTextSpan> = emptyList(), closure: VMDTextViewModelImpl.() -> Unit = {}) =
    VMDComponents.Text.withSpans(content, spans, coroutineScope, closure)

fun VMDViewModelDSL.localImage(image: VMDImageResource = VMDImageResource.None, contentDescription: String? = null, closure: VMDImageViewModelImpl.() -> Unit = {}) =
    VMDComponents.Image.local(image, coroutineScope, contentDescription, closure)

fun VMDViewModelDSL.remoteImage(
    imageUrl: String? = null,
    placeholderImageResource: VMDImageResource = VMDImageResource.None,
    contentDescription: String? = null,
    closure: VMDImageViewModelImpl.() -> Unit = {}
) =
    VMDComponents.Image.remote(imageUrl, placeholderImageResource, coroutineScope, contentDescription, closure)

fun VMDViewModelDSL.image(imageDescriptor: VMDImageDescriptor = VMDImageDescriptor.Local(VMDImageResource.None), contentDescription: String? = null, closure: VMDImageViewModelImpl.() -> Unit = {}) =
    VMDComponents.Image.withDescriptor(imageDescriptor, coroutineScope, contentDescription, closure)

fun VMDViewModelDSL.button(closure: VMDButtonViewModelImpl<VMDNoContent>.() -> Unit = {}) =
    VMDComponents.Button.empty(coroutineScope, closure)

fun VMDViewModelDSL.buttonWithText(text: String = "", action: () -> Unit = {}, closure: VMDButtonViewModelImpl<VMDTextContent>.() -> Unit = {}) =
    VMDComponents.Button.withText(text, coroutineScope).apply {
        setAction(action)
        closure()
    }

fun VMDViewModelDSL.buttonWithImage(image: VMDImageResource = VMDImageResource.None, action: () -> Unit = {}, closure: VMDButtonViewModelImpl<VMDImageContent>.() -> Unit = {}) =
    VMDComponents.Button.withImage(image, coroutineScope).apply {
        setAction(action)
        closure()
    }

fun VMDViewModelDSL.buttonWithImage(
    initialImage: VMDImageResource,
    image: Flow<VMDImageResource>,
    action: () -> Unit,
    closure: VMDButtonViewModelImpl<VMDImageContent>.() -> Unit = {}
) =
    VMDComponents.Button.withImage(initialImage, coroutineScope).apply {
        bindContent(image.map { VMDImageContent(it) })
        setAction(action)
        closure()
    }

fun VMDViewModelDSL.buttonWithImageUrl(
    imageUrl: String = "",
    placeholderImageResource: VMDImageResource = VMDImageResource.None,
    action: () -> Unit = {},
    closure: VMDButtonViewModelImpl<VMDImageDescriptorContent>.() -> Unit = {}
) =
    VMDComponents.Button.withImageUrl(imageUrl, placeholderImageResource, coroutineScope).apply {
        setAction(action)
        closure()
    }

fun VMDViewModelDSL.buttonWithTextPair(first: String = "", second: String = "", action: () -> Unit = {}, closure: VMDButtonViewModelImpl<VMDTextPairContent>.() -> Unit = {}) =
    VMDComponents.Button.withTextPair(
        VMDTextPairContent(
            first,
            second
        ),
        coroutineScope
    ).apply {
        setAction(action)
        closure()
    }

fun VMDViewModelDSL.buttonWithTextImage(
    text: String = "",
    image: VMDImageResource = VMDImageResource.None,
    action: () -> Unit = {},
    closure: VMDButtonViewModelImpl<VMDTextImagePairContent>.() -> Unit = {}
) =
    VMDComponents.Button.withTextImage(
        VMDTextImagePairContent(
            text,
            image
        ),
        coroutineScope
    ).apply {
        setAction(action)
        closure()
    }

fun VMDViewModelDSL.textField(text: String = "", placeholder: String = "", closure: VMDTextFieldViewModelImpl.() -> Unit = {}) = VMDComponents.TextField.empty(coroutineScope).apply {
    this.text = text
    this.placeholder = placeholder
    closure()
}

fun VMDViewModelDSL.toggle(closure: VMDToggleViewModelImpl<VMDNoContent>.() -> Unit = {}) =
    VMDComponents.Toggle.empty(coroutineScope, closure)

fun VMDViewModelDSL.toggleWithText(text: String = "", isOn: Boolean = false, closure: VMDToggleViewModelImpl<VMDTextContent>.() -> Unit = {}) =
    VMDComponents.Toggle.withText(text, isOn, coroutineScope, closure)

fun VMDViewModelDSL.toggleWithImage(image: VMDImageResource = VMDImageResource.None, isOn: Boolean = false, closure: VMDToggleViewModelImpl<VMDImageContent>.() -> Unit = {}) =
    VMDComponents.Toggle.withImage(image, isOn, coroutineScope, closure)

fun VMDViewModelDSL.toggleWithTextImage(
    text: String = "",
    image: VMDImageResource = VMDImageResource.None,
    isOn: Boolean = false,
    closure: VMDToggleViewModelImpl<VMDTextImagePairContent>.() -> Unit = {}
) =
    VMDComponents.Toggle.withTextImage(VMDTextImagePairContent(text, image), isOn, coroutineScope, closure)

fun VMDViewModelDSL.toggleWithTextPair(
    first: String = "",
    second: String = "",
    isOn: Boolean = false,
    closure: VMDToggleViewModelImpl<VMDTextPairContent>.() -> Unit = {}
) =
    VMDComponents.Toggle.withTextPair(VMDTextPairContent(first, second), isOn, coroutineScope, closure)

fun VMDViewModelDSL.loading(isLoading: Boolean = false, closure: VMDLoadingViewModelImpl.() -> Unit = {}) = VMDLoadingViewModelImpl(coroutineScope)
    .apply {
        this.isLoading = isLoading
        closure()
    }

fun VMDViewModelDSL.loading(isLoading: Flow<Boolean>, closure: VMDLoadingViewModelImpl.() -> Unit = {}) = VMDLoadingViewModelImpl(coroutineScope)
    .apply {
        bindIsLoading(isLoading)
        closure()
    }

fun VMDViewModelDSL.indeterminateProgress(closure: VMDProgressViewModelImpl.() -> Unit = {}) =
    VMDComponents.Progress.indeterminate(coroutineScope, closure)

fun VMDViewModelDSL.determinateProgress(progress: Float = 0f, total: Float = 1f, closure: VMDProgressViewModelImpl.() -> Unit = {}) =
    VMDComponents.Progress.determinate(progress, total, coroutineScope, closure)

fun <C : VMDIdentifiableContent> VMDViewModelDSL.list(elements: List<C> = emptyList(), closure: VMDListViewModelImpl<C>.() -> Unit = {}) =
    VMDComponents.List.empty<C>(coroutineScope).apply {
        this.elements = elements
        closure()
    }

fun <C : VMDIdentifiableContent> VMDViewModelDSL.list(vararg elements: C, closure: VMDListViewModelImpl<C>.() -> Unit = {}) =
    VMDComponents.List.empty<C>(coroutineScope).apply {
        this.elements = elements.toList()
        closure()
    }

fun <C : VMDIdentifiableContent> VMDViewModelDSL.list(elements: Flow<List<C>>, closure: VMDListViewModelImpl<C>.() -> Unit = {}) =
    VMDComponents.List.empty<C>(coroutineScope).apply {
        bindElements(elements)
        closure()
    }

fun <E : VMDPickerItemViewModel> VMDViewModelDSL.picker(elements: List<E> = emptyList(), initialSelectedId: String? = null, closure: VMDPickerViewModelImpl<E>.() -> Unit = {}) =
    VMDComponents.Picker.withElements(coroutineScope, elements, initialSelectedId, closure)
