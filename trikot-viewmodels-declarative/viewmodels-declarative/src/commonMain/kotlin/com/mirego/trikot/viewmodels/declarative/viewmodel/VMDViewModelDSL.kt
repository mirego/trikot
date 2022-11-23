package com.mirego.trikot.viewmodels.declarative.viewmodel

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.map
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
import org.reactivestreams.Publisher

interface VMDViewModelDSL {
    val cancellableManager: CancellableManager
}

fun VMDViewModelDSL.text(content: String = "", closure: VMDTextViewModelImpl.() -> Unit = {}) =
    VMDComponents.Text.withContent(content, cancellableManager, closure)

fun VMDViewModelDSL.localImage(image: VMDImageResource = VMDImageResource.None, closure: VMDImageViewModelImpl.() -> Unit = {}) =
    VMDComponents.Image.local(image, cancellableManager, closure)

fun VMDViewModelDSL.remoteImage(imageUrl: String? = null, placeholderImageResource: VMDImageResource = VMDImageResource.None, closure: VMDImageViewModelImpl.() -> Unit = {}) =
    VMDComponents.Image.remote(imageUrl, placeholderImageResource, cancellableManager, closure)

fun VMDViewModelDSL.image(imageDescriptor: VMDImageDescriptor = VMDImageDescriptor.Local(VMDImageResource.None), closure: VMDImageViewModelImpl.() -> Unit = {}) =
    VMDComponents.Image.withDescriptor(imageDescriptor, cancellableManager, closure)

fun VMDViewModelDSL.button(closure: VMDButtonViewModelImpl<VMDNoContent>.() -> Unit = {}) =
    VMDComponents.Button.empty(cancellableManager, closure)

fun VMDViewModelDSL.buttonWithText(text: String = "", action: () -> Unit = {}, closure: VMDButtonViewModelImpl<VMDTextContent>.() -> Unit = {}) =
    VMDComponents.Button.withText(text, cancellableManager, closure).apply {
        setAction(action)
    }

fun VMDViewModelDSL.buttonWithImage(image: VMDImageResource = VMDImageResource.None, action: () -> Unit = {}, closure: VMDButtonViewModelImpl<VMDImageContent>.() -> Unit = {}) =
    VMDComponents.Button.withImage(image, cancellableManager, closure).apply {
        setAction(action)
    }

fun VMDViewModelDSL.buttonWithImage(
    initialImage: VMDImageResource,
    image: Publisher<VMDImageResource>,
    action: () -> Unit,
    closure: VMDButtonViewModelImpl<VMDImageContent>.() -> Unit = {}
) =
    VMDComponents.Button.withImage(initialImage, cancellableManager, closure).apply {
        bindContent(image.map { VMDImageContent(it) })
        setAction(action)
    }

fun VMDViewModelDSL.buttonWithImageUrl(
    imageUrl: String = "",
    placeholderImageResource: VMDImageResource = VMDImageResource.None,
    action: () -> Unit = {},
    closure: VMDButtonViewModelImpl<VMDImageDescriptorContent>.() -> Unit = {}
) =
    VMDComponents.Button.withImageUrl(imageUrl, placeholderImageResource, cancellableManager, closure).apply {
        setAction(action)
    }

fun VMDViewModelDSL.buttonWithTextPair(first: String = "", second: String = "", action: () -> Unit = {}, closure: VMDButtonViewModelImpl<VMDTextPairContent>.() -> Unit = {}) =
    VMDComponents.Button.withTextPair(
        VMDTextPairContent(
            first,
            second
        ),
        cancellableManager,
        closure
    ).apply {
        setAction(action)
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
        cancellableManager,
        closure
    ).apply {
        setAction(action)
    }

fun VMDViewModelDSL.textField(text: String = "", placeholder: String = "", closure: VMDTextFieldViewModelImpl.() -> Unit = {}) = VMDComponents.TextField.empty(cancellableManager, closure).apply {
    this.text = text
    this.placeholder = placeholder
}

fun VMDViewModelDSL.toggle(closure: VMDToggleViewModelImpl<VMDNoContent>.() -> Unit = {}) =
    VMDComponents.Toggle.empty(cancellableManager, closure)

fun VMDViewModelDSL.toggleWithText(text: String = "", isOn: Boolean = false, closure: VMDToggleViewModelImpl<VMDTextContent>.() -> Unit = {}) =
    VMDComponents.Toggle.withText(text, isOn, cancellableManager, closure)

fun VMDViewModelDSL.toggleWithImage(image: VMDImageResource = VMDImageResource.None, isOn: Boolean = false, closure: VMDToggleViewModelImpl<VMDImageContent>.() -> Unit = {}) =
    VMDComponents.Toggle.withImage(image, isOn, cancellableManager, closure)

fun VMDViewModelDSL.toggleWithTextImage(
    text: String = "",
    image: VMDImageResource = VMDImageResource.None,
    isOn: Boolean = false,
    closure: VMDToggleViewModelImpl<VMDTextImagePairContent>.() -> Unit = {}
) =
    VMDComponents.Toggle.withTextImage(VMDTextImagePairContent(text, image), isOn, cancellableManager, closure)

fun VMDViewModelDSL.toggleWithTextPair(
    first: String = "",
    second: String = "",
    isOn: Boolean = false,
    closure: VMDToggleViewModelImpl<VMDTextPairContent>.() -> Unit = {}
) =
    VMDComponents.Toggle.withTextPair(VMDTextPairContent(first, second), isOn, cancellableManager, closure)

fun VMDViewModelDSL.loading(isLoading: Boolean = false, closure: VMDLoadingViewModelImpl.() -> Unit = {}) = VMDLoadingViewModelImpl(cancellableManager)
    .apply {
        this.isLoading = isLoading
        closure()
    }

fun VMDViewModelDSL.loading(isLoading: Publisher<Boolean>, closure: VMDLoadingViewModelImpl.() -> Unit = {}) = VMDLoadingViewModelImpl(cancellableManager)
    .apply {
        bindIsLoading(isLoading)
        closure()
    }

fun VMDViewModelDSL.indeterminateProgress(closure: VMDProgressViewModelImpl.() -> Unit = {}) =
    VMDComponents.Progress.indeterminate(cancellableManager, closure)

fun VMDViewModelDSL.determinateProgress(progress: Float = 0f, total: Float = 1f, closure: VMDProgressViewModelImpl.() -> Unit = {}) =
    VMDComponents.Progress.determinate(progress, total, cancellableManager, closure)

fun <C : VMDIdentifiableContent> VMDViewModelDSL.list(elements: List<C> = emptyList(), closure: VMDListViewModelImpl<C>.() -> Unit = {}) =
    VMDComponents.List.empty(cancellableManager, closure).apply {
        this.elements = elements
    }

fun <C : VMDIdentifiableContent> VMDViewModelDSL.list(vararg elements: C, closure: VMDListViewModelImpl<C>.() -> Unit = {}) =
    VMDComponents.List.empty(cancellableManager, closure).apply {
        this.elements = elements.toList()
    }

fun <C : VMDIdentifiableContent> VMDViewModelDSL.list(elements: Publisher<List<C>>, closure: VMDListViewModelImpl<C>.() -> Unit = {}) =
    VMDComponents.List.empty(cancellableManager, closure).apply {
        bindElements(elements)
    }

fun <E : VMDPickerItemViewModel> VMDViewModelDSL.picker(elements: List<E> = emptyList(), initialSelectedId: String? = null, closure: VMDPickerViewModelImpl<E>.() -> Unit = {}) =
    VMDComponents.Picker.withElements(cancellableManager, elements, initialSelectedId, closure)
