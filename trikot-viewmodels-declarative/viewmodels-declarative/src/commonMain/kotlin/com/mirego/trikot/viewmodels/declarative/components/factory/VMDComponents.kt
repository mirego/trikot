package com.mirego.trikot.viewmodels.declarative.components.factory

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.VMDPickerItemViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDPickerViewModel
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
import com.mirego.trikot.viewmodels.declarative.properties.VMDProgressDetermination

object VMDComponents {
    class Text {
        companion object {
            fun empty(
                cancellableManager: CancellableManager,
                closure: VMDTextViewModelImpl.() -> Unit = {}
            ) =
                VMDTextViewModelImpl(cancellableManager)
                    .apply(closure)

            fun withContent(
                content: String,
                cancellableManager: CancellableManager,
                closure: VMDTextViewModelImpl.() -> Unit = {}
            ) =
                VMDTextViewModelImpl(cancellableManager)
                    .apply { text = content }
                    .apply(closure)
        }
    }

    class Image {
        companion object {
            fun empty(
                cancellableManager: CancellableManager,
                closure: VMDImageViewModelImpl.() -> Unit = {}
            ) =
                VMDImageViewModelImpl(cancellableManager)
                    .apply(closure)

            fun local(
                imageResource: VMDImageResource,
                cancellableManager: CancellableManager,
                closure: VMDImageViewModelImpl.() -> Unit = {}
            ) =
                VMDImageViewModelImpl(cancellableManager)
                    .apply { image = VMDImageDescriptor.Local(imageResource) }
                    .apply(closure)

            fun remote(
                imageURL: String?,
                placeholderImageResource: VMDImageResource = VMDImageResource.None,
                cancellableManager: CancellableManager,
                closure: VMDImageViewModelImpl.() -> Unit = {}
            ) =
                VMDImageViewModelImpl(cancellableManager)
                    .apply {
                        image = VMDImageDescriptor.Remote(imageURL, placeholderImageResource)
                    }
                    .apply(closure)

            fun withDescriptor(
                imageDescriptor: VMDImageDescriptor,
                cancellableManager: CancellableManager,
                closure: VMDImageViewModelImpl.() -> Unit = {}
            ) = VMDImageViewModelImpl(cancellableManager)
                .apply {
                    image = imageDescriptor
                }
                .apply(closure)
        }
    }

    class Button {
        companion object {
            fun empty(
                cancellableManager: CancellableManager,
                closure: VMDButtonViewModelImpl<VMDNoContent>.() -> Unit = {}
            ) =
                VMDButtonViewModelImpl(cancellableManager, VMDNoContent())
                    .apply(closure)

            fun withText(
                text: String,
                cancellableManager: CancellableManager,
                closure: VMDButtonViewModelImpl<VMDTextContent>.() -> Unit = {}
            ) =
                VMDButtonViewModelImpl(cancellableManager, VMDTextContent(text))
                    .apply(closure)

            fun withImage(
                image: VMDImageResource,
                cancellableManager: CancellableManager,
                closure: VMDButtonViewModelImpl<VMDImageContent>.() -> Unit = {}
            ) =
                VMDButtonViewModelImpl(
                    cancellableManager,
                    VMDImageContent(image)
                )
                    .apply(closure)

            fun withImageUrl(
                imageUrl: String,
                placeholderImageResource: VMDImageResource = VMDImageResource.None,
                cancellableManager: CancellableManager,
                closure: VMDButtonViewModelImpl<VMDImageDescriptorContent>.() -> Unit = {}
            ) =
                VMDButtonViewModelImpl(
                    cancellableManager,
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
                cancellableManager: CancellableManager,
                closure: VMDButtonViewModelImpl<VMDTextPairContent>.() -> Unit = {}
            ) =
                VMDButtonViewModelImpl(cancellableManager, textPair)
                    .apply(closure)

            fun withTextImage(
                textImage: VMDTextImagePairContent,
                cancellableManager: CancellableManager,
                closure: VMDButtonViewModelImpl<VMDTextImagePairContent>.() -> Unit = {}
            ) =
                VMDButtonViewModelImpl(cancellableManager, textImage)
                    .apply(closure)
        }
    }

    class TextField {
        companion object {
            fun empty(
                cancellableManager: CancellableManager,
                closure: VMDTextFieldViewModelImpl.() -> Unit = {}
            ) =
                VMDTextFieldViewModelImpl(cancellableManager)
                    .apply(closure)

            fun withPlaceholder(
                placeholderText: String,
                cancellableManager: CancellableManager,
                closure: VMDTextFieldViewModelImpl.() -> Unit = {}
            ) =
                VMDTextFieldViewModelImpl(cancellableManager)
                    .apply { placeholder = placeholderText }
                    .apply(closure)
        }
    }

    class Toggle {
        companion object {
            fun empty(
                cancellableManager: CancellableManager,
                closure: VMDToggleViewModelImpl<VMDNoContent>.() -> Unit = {}
            ) =
                VMDToggleViewModelImpl(cancellableManager, VMDNoContent())
                    .apply(closure)

            fun withState(
                state: Boolean,
                cancellableManager: CancellableManager,
                closure: VMDToggleViewModelImpl<VMDNoContent>.() -> Unit = {}
            ) =
                VMDToggleViewModelImpl(cancellableManager, VMDNoContent())
                    .apply { isOn = state }
                    .apply(closure)

            fun withText(
                text: String,
                state: Boolean,
                cancellableManager: CancellableManager,
                closure: VMDToggleViewModelImpl<VMDTextContent>.() -> Unit = {}
            ) =
                VMDToggleViewModelImpl(cancellableManager, VMDTextContent(text))
                    .apply { isOn = state }
                    .apply(closure)

            fun withImage(
                image: VMDImageResource,
                state: Boolean,
                cancellableManager: CancellableManager,
                closure: VMDToggleViewModelImpl<VMDImageContent>.() -> Unit = {}
            ) =
                VMDToggleViewModelImpl(
                    cancellableManager,
                    VMDImageContent(image)
                )
                    .apply { isOn = state }
                    .apply(closure)

            fun withTextPair(
                textPair: VMDTextPairContent,
                state: Boolean,
                cancellableManager: CancellableManager,
                closure: VMDToggleViewModelImpl<VMDTextPairContent>.() -> Unit = {}
            ) =
                VMDToggleViewModelImpl(cancellableManager, textPair)
                    .apply { isOn = state }
                    .apply(closure)

            fun withTextImage(
                textImage: VMDTextImagePairContent,
                state: Boolean,
                cancellableManager: CancellableManager,
                closure: VMDToggleViewModelImpl<VMDTextImagePairContent>.() -> Unit = {}
            ) =
                VMDToggleViewModelImpl(cancellableManager, textImage)
                    .apply { isOn = state }
                    .apply(closure)
        }
    }

    class Loading {
        companion object {
            fun inactive(
                cancellableManager: CancellableManager,
                closure: VMDLoadingViewModelImpl.() -> Unit = {}
            ) =
                VMDLoadingViewModelImpl(cancellableManager)
                    .apply(closure)

            fun active(
                cancellableManager: CancellableManager,
                closure: VMDLoadingViewModelImpl.() -> Unit = {}
            ) =
                VMDLoadingViewModelImpl(cancellableManager)
                    .apply { isLoading = true }
                    .apply(closure)
        }
    }

    class Progress {
        companion object {
            fun indeterminate(
                cancellableManager: CancellableManager,
                closure: VMDProgressViewModelImpl.() -> Unit = {}
            ) =
                VMDProgressViewModelImpl(cancellableManager)
                    .apply(closure)

            fun determinate(
                progress: Float,
                cancellableManager: CancellableManager,
                closure: VMDProgressViewModelImpl.() -> Unit = {}
            ) =
                VMDProgressViewModelImpl(cancellableManager)
                    .apply { determination = VMDProgressDetermination(progress, 1f) }
                    .apply(closure)

            fun determinate(
                progress: Float,
                total: Float,
                cancellableManager: CancellableManager,
                closure: VMDProgressViewModelImpl.() -> Unit = {}
            ) =
                VMDProgressViewModelImpl(cancellableManager)
                    .apply { determination = VMDProgressDetermination(progress, total) }
                    .apply(closure)
        }
    }

    class List {
        companion object {
            fun <C : VMDIdentifiableContent> empty(
                cancellableManager: CancellableManager,
                closure: VMDListViewModelImpl<C>.() -> Unit = {}
            ) =
                VMDListViewModelImpl<C>(cancellableManager)
                    .apply(closure)

            fun <C : VMDIdentifiableContent> of(
                vararg listElements: C,
                cancellableManager: CancellableManager,
                closure: VMDListViewModelImpl<C>.() -> Unit = {}
            ) =
                VMDListViewModelImpl<C>(cancellableManager)
                    .apply { elements = listElements.asList() }
                    .apply(closure)
        }
    }

    class Picker {
        companion object {
            fun <E : VMDPickerItemViewModel> withElements(
                cancellableManager: CancellableManager,
                elements: kotlin.collections.List<E>,
                initialSelectedId: String? = null
            ): VMDPickerViewModel<E> =
                VMDPickerViewModelImpl(cancellableManager, elements, initialSelectedId)
        }
    }
}
