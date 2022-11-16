package com.mirego.trikot.viewmodels.declarative.viewmodel

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.map
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDButtonViewModelImpl
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDImageViewModelImpl
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDTextViewModelImpl
import com.mirego.trikot.viewmodels.declarative.content.VMDIdentifiableContent
import com.mirego.trikot.viewmodels.declarative.content.VMDImageContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent
import com.mirego.trikot.viewmodels.declarative.content.VMDTextImagePairContent
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageResource
import org.reactivestreams.Publisher

interface VMDViewModelDSL {
    val cancellableManager: CancellableManager
}

fun VMDViewModelDSL.text(content: String, closure: VMDTextViewModelImpl.() -> Unit = {}) =
    VMDComponents.Text.withContent(content, cancellableManager, closure)

fun VMDViewModelDSL.buttonWithImage(image: VMDImageResource, action: () -> Unit, closure: VMDButtonViewModelImpl<VMDImageContent>.() -> Unit = {}) =
    VMDComponents.Button.withImage(image, cancellableManager, closure).apply {
        setAction(action)
    }

fun VMDViewModelDSL.localImage(image: VMDImageResource) =
    VMDComponents.Image.local(image, cancellableManager)

fun VMDViewModelDSL.remoteImage(imageUrl: String?, placeholderImageResource: VMDImageResource = VMDImageResource.None, closure: VMDImageViewModelImpl.() -> Unit = {}) =
    VMDComponents.Image.remote(imageUrl, placeholderImageResource, cancellableManager, closure)

fun <C : VMDIdentifiableContent> VMDViewModelDSL.list(elements: List<C>) =
    VMDComponents.List.empty<C>(cancellableManager) {
        this.elements = elements
    }

fun <C : VMDIdentifiableContent> VMDViewModelDSL.list(elements: Publisher<List<C>>) =
    VMDComponents.List.empty<C>(cancellableManager) {
        bindElements(elements)
    }

fun VMDViewModelDSL.buttonWithText(text: String, action: () -> Unit, closure: VMDButtonViewModelImpl<VMDTextContent>.() -> Unit = {}) =
    VMDComponents.Button.withText(text, cancellableManager, closure).apply {
        setAction(action)
    }

fun VMDViewModelDSL.buttonWithTextImage(text: String, image: VMDImageResource, action: () -> Unit, closure: VMDButtonViewModelImpl<VMDTextImagePairContent>.() -> Unit = {}) =
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
