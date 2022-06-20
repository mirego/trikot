package com.mirego.trikot.viewmodels.declarative.components.factory

import com.mirego.trikot.viewmodels.declarative.components.impl.VMDButtonViewModelImpl
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDImageViewModelImpl
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDListViewModelImpl
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDLoadingViewModelImpl
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
import com.mirego.trikot.viewmodels.declarative.properties.VMDProgressDetermination
import kotlinx.coroutines.CoroutineScope

object VMDComponents {
    class Text {
        companion object {
            fun empty(
                coroutineScope: CoroutineScope,
                closure: VMDTextViewModelImpl.() -> Unit = {}
            ) =
                VMDTextViewModelImpl(coroutineScope)
                    .apply(closure)

            fun withContent(
                content: String,
                coroutineScope: CoroutineScope,
                closure: VMDTextViewModelImpl.() -> Unit = {}
            ) =
                VMDTextViewModelImpl(coroutineScope)
                    .apply { text = content }
                    .apply(closure)
        }
    }

    class Image {
        companion object {
            fun empty(
                coroutineScope: CoroutineScope,
                closure: VMDImageViewModelImpl.() -> Unit = {}
            ) =
                VMDImageViewModelImpl(coroutineScope)
                    .apply(closure)

            fun local(
                imageResource: VMDImageResource,
                coroutineScope: CoroutineScope,
                closure: VMDImageViewModelImpl.() -> Unit = {}
            ) =
                VMDImageViewModelImpl(coroutineScope)
                    .apply { image = VMDImageDescriptor.Local(imageResource) }
                    .apply(closure)

            fun remote(
                imageURL: String?,
                placeholderImageResource: VMDImageResource = VMDImageResource.None,
                coroutineScope: CoroutineScope,
                closure: VMDImageViewModelImpl.() -> Unit = {}
            ) =
                VMDImageViewModelImpl(coroutineScope)
                    .apply {
                        image = VMDImageDescriptor.Remote(imageURL, placeholderImageResource)
                    }
                    .apply(closure)

            fun withDescriptor(
                imageDescriptor: VMDImageDescriptor,
                coroutineScope: CoroutineScope,
                closure: VMDImageViewModelImpl.() -> Unit = {}
            ) = VMDImageViewModelImpl(coroutineScope)
                .apply {
                    image = imageDescriptor
                }
                .apply(closure)
        }
    }

    class Button {
        companion object {
            fun empty(
                coroutineScope: CoroutineScope,
                closure: VMDButtonViewModelImpl<VMDNoContent>.() -> Unit = {}
            ) =
                VMDButtonViewModelImpl(coroutineScope, VMDNoContent())
                    .apply(closure)

            fun withText(
                text: String,
                coroutineScope: CoroutineScope,
                closure: VMDButtonViewModelImpl<VMDTextContent>.() -> Unit = {}
            ) =
                VMDButtonViewModelImpl(coroutineScope, VMDTextContent(text))
                    .apply(closure)

            fun withImage(
                image: VMDImageResource,
                coroutineScope: CoroutineScope,
                closure: VMDButtonViewModelImpl<VMDImageContent>.() -> Unit = {}
            ) =
                VMDButtonViewModelImpl(
                    coroutineScope,
                    VMDImageContent(image)
                )
                    .apply(closure)

            fun withImageUrl(
                imageUrl: String,
                placeholderImageResource: VMDImageResource = VMDImageResource.None,
                coroutineScope: CoroutineScope,
                closure: VMDButtonViewModelImpl<VMDImageDescriptorContent>.() -> Unit = {}
            ) =
                VMDButtonViewModelImpl(
                    coroutineScope,
                    VMDImageDescriptorContent(
                        VMDImageDescriptor.Remote(
                            imageUrl,
                            placeholderImageResource = placeholderImageResource
                        )
                    )
                )
                    .apply(closure)

            fun withTextPair(
                textPair: VMDTextPairContent,
                coroutineScope: CoroutineScope,
                closure: VMDButtonViewModelImpl<VMDTextPairContent>.() -> Unit = {}
            ) =
                VMDButtonViewModelImpl(coroutineScope, textPair)
                    .apply(closure)

            fun withTextImage(
                textImage: VMDTextImagePairContent,
                coroutineScope: CoroutineScope,
                closure: VMDButtonViewModelImpl<VMDTextImagePairContent>.() -> Unit = {}
            ) =
                VMDButtonViewModelImpl(coroutineScope, textImage)
                    .apply(closure)
        }
    }

    class TextField {
        companion object {
            fun empty(
                coroutineScope: CoroutineScope,
                closure: VMDTextFieldViewModelImpl.() -> Unit = {}
            ) =
                VMDTextFieldViewModelImpl(coroutineScope)
                    .apply(closure)

            fun withPlaceholder(
                placeholderText: String,
                coroutineScope: CoroutineScope,
                closure: VMDTextFieldViewModelImpl.() -> Unit = {}
            ) =
                VMDTextFieldViewModelImpl(coroutineScope)
                    .apply { placeholder = placeholderText }
                    .apply(closure)
        }
    }

    class Toggle {
        companion object {
            fun empty(
                coroutineScope: CoroutineScope,
                closure: VMDToggleViewModelImpl<VMDNoContent>.() -> Unit = {}
            ) =
                VMDToggleViewModelImpl(coroutineScope, VMDNoContent())
                    .apply(closure)

            fun withState(
                state: Boolean,
                coroutineScope: CoroutineScope,
                closure: VMDToggleViewModelImpl<VMDNoContent>.() -> Unit = {}
            ) =
                VMDToggleViewModelImpl(coroutineScope, VMDNoContent())
                    .apply { isOn = state }
                    .apply(closure)

            fun withText(
                text: String,
                state: Boolean,
                coroutineScope: CoroutineScope,
                closure: VMDToggleViewModelImpl<VMDTextContent>.() -> Unit = {}
            ) =
                VMDToggleViewModelImpl(coroutineScope, VMDTextContent(text))
                    .apply { isOn = state }
                    .apply(closure)

            fun withImage(
                image: VMDImageResource,
                state: Boolean,
                coroutineScope: CoroutineScope,
                closure: VMDToggleViewModelImpl<VMDImageContent>.() -> Unit = {}
            ) =
                VMDToggleViewModelImpl(
                    coroutineScope,
                    VMDImageContent(image)
                )
                    .apply { isOn = state }
                    .apply(closure)

            fun withTextPair(
                textPair: VMDTextPairContent,
                state: Boolean,
                coroutineScope: CoroutineScope,
                closure: VMDToggleViewModelImpl<VMDTextPairContent>.() -> Unit = {}
            ) =
                VMDToggleViewModelImpl(coroutineScope, textPair)
                    .apply { isOn = state }
                    .apply(closure)

            fun withTextImage(
                textImage: VMDTextImagePairContent,
                state: Boolean,
                coroutineScope: CoroutineScope,
                closure: VMDToggleViewModelImpl<VMDTextImagePairContent>.() -> Unit = {}
            ) =
                VMDToggleViewModelImpl(coroutineScope, textImage)
                    .apply { isOn = state }
                    .apply(closure)
        }
    }

    class Loading {
        companion object {
            fun inactive(
                coroutineScope: CoroutineScope,
                closure: VMDLoadingViewModelImpl.() -> Unit = {}
            ) =
                VMDLoadingViewModelImpl(coroutineScope)
                    .apply(closure)

            fun active(
                coroutineScope: CoroutineScope,
                closure: VMDLoadingViewModelImpl.() -> Unit = {}
            ) =
                VMDLoadingViewModelImpl(coroutineScope)
                    .apply { isLoading = true }
                    .apply(closure)
        }
    }

    class Progress {
        companion object {
            fun indeterminate(
                coroutineScope: CoroutineScope,
                closure: VMDProgressViewModelImpl.() -> Unit = {}
            ) =
                VMDProgressViewModelImpl(coroutineScope)
                    .apply(closure)

            fun determinate(
                progress: Float,
                coroutineScope: CoroutineScope,
                closure: VMDProgressViewModelImpl.() -> Unit = {}
            ) =
                VMDProgressViewModelImpl(coroutineScope)
                    .apply { determination = VMDProgressDetermination(progress, 1f) }
                    .apply(closure)

            fun determinate(
                progress: Float,
                total: Float,
                coroutineScope: CoroutineScope,
                closure: VMDProgressViewModelImpl.() -> Unit = {}
            ) =
                VMDProgressViewModelImpl(coroutineScope)
                    .apply { determination = VMDProgressDetermination(progress, total) }
                    .apply(closure)
        }
    }

    class List {
        companion object {
            fun <C : VMDIdentifiableContent> empty(
                coroutineScope: CoroutineScope,
                closure: VMDListViewModelImpl<C>.() -> Unit = {}
            ) =
                VMDListViewModelImpl<C>(coroutineScope)
                    .apply(closure)

            fun <C : VMDIdentifiableContent> of(
                vararg listElements: C,
                coroutineScope: CoroutineScope,
                closure: VMDListViewModelImpl<C>.() -> Unit = {}
            ) =
                VMDListViewModelImpl<C>(coroutineScope)
                    .apply { elements = listElements.asList() }
                    .apply(closure)
        }
    }
}
