package com.mirego.trikot.viewmodels.declarative.components.factory

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.impl.ButtonViewModelImpl
import com.mirego.trikot.viewmodels.declarative.components.impl.ImageViewModelImpl
import com.mirego.trikot.viewmodels.declarative.components.impl.ListViewModelImpl
import com.mirego.trikot.viewmodels.declarative.components.impl.LoadingViewModelImpl
import com.mirego.trikot.viewmodels.declarative.components.impl.ProgressViewModelImpl
import com.mirego.trikot.viewmodels.declarative.components.impl.TextFieldViewModelImpl
import com.mirego.trikot.viewmodels.declarative.components.impl.TextViewModelImpl
import com.mirego.trikot.viewmodels.declarative.components.impl.ToggleViewModelImpl
import com.mirego.trikot.viewmodels.declarative.content.IdentifiableContent
import com.mirego.trikot.viewmodels.declarative.content.ImageContent
import com.mirego.trikot.viewmodels.declarative.content.NoContent
import com.mirego.trikot.viewmodels.declarative.content.TextContent
import com.mirego.trikot.viewmodels.declarative.content.TextImagePairContent
import com.mirego.trikot.viewmodels.declarative.content.TextPairContent
import com.mirego.trikot.viewmodels.declarative.properties.ImageDescriptor
import com.mirego.trikot.viewmodels.declarative.properties.ImageResource
import com.mirego.trikot.viewmodels.declarative.properties.ProgressDetermination

class ComponentsFactory {
    companion object {
        class Text {
            companion object {
                fun empty(
                    cancellableManager: CancellableManager,
                    closure: TextViewModelImpl.() -> Unit = {}
                ) =
                    TextViewModelImpl(cancellableManager)
                        .apply(closure)

                fun withContent(
                    content: String,
                    cancellableManager: CancellableManager,
                    closure: TextViewModelImpl.() -> Unit = {}
                ) =
                    TextViewModelImpl(cancellableManager)
                        .apply { text = content }
                        .apply(closure)
            }
        }

        class Image {
            companion object {
                fun empty(
                    cancellableManager: CancellableManager,
                    closure: ImageViewModelImpl.() -> Unit = {}
                ) =
                    ImageViewModelImpl(cancellableManager)
                        .apply(closure)

                fun local(
                    imageResource: ImageResource,
                    cancellableManager: CancellableManager,
                    closure: ImageViewModelImpl.() -> Unit = {}
                ) =
                    ImageViewModelImpl(cancellableManager)
                        .apply { image = ImageDescriptor.Local(imageResource) }
                        .apply(closure)

                fun remote(
                    imageURL: String,
                    placeholderImageResource: ImageResource = ImageResource.None,
                    cancellableManager: CancellableManager,
                    closure: ImageViewModelImpl.() -> Unit = {}
                ) =
                    ImageViewModelImpl(cancellableManager)
                        .apply {
                            image = ImageDescriptor.Remote(imageURL, placeholderImageResource)
                        }
                        .apply(closure)
            }
        }

        class Button {
            companion object {
                fun empty(
                    cancellableManager: CancellableManager,
                    closure: ButtonViewModelImpl<NoContent>.() -> Unit = {}
                ) =
                    ButtonViewModelImpl(cancellableManager, NoContent())
                        .apply(closure)

                fun withText(
                    text: String,
                    cancellableManager: CancellableManager,
                    closure: ButtonViewModelImpl<TextContent>.() -> Unit = {}
                ) =
                    ButtonViewModelImpl(cancellableManager, TextContent(text))
                        .apply(closure)

                fun withImage(
                    image: ImageResource,
                    cancellableManager: CancellableManager,
                    closure: ButtonViewModelImpl<ImageContent>.() -> Unit = {}
                ) =
                    ButtonViewModelImpl(cancellableManager, ImageContent(image))
                        .apply(closure)

                fun withTextPair(
                    textPair: TextPairContent,
                    cancellableManager: CancellableManager,
                    closure: ButtonViewModelImpl<TextPairContent>.() -> Unit = {}
                ) =
                    ButtonViewModelImpl(cancellableManager, textPair)
                        .apply(closure)

                fun withTextImage(
                    textImage: TextImagePairContent,
                    cancellableManager: CancellableManager,
                    closure: ButtonViewModelImpl<TextImagePairContent>.() -> Unit = {}
                ) =
                    ButtonViewModelImpl(cancellableManager, textImage)
                        .apply(closure)
            }
        }

        class TextField {
            companion object {
                fun empty(
                    cancellableManager: CancellableManager,
                    closure: TextFieldViewModelImpl.() -> Unit = {}
                ) =
                    TextFieldViewModelImpl(cancellableManager)
                        .apply(closure)

                fun withPlaceholder(
                    placeholderText: String,
                    cancellableManager: CancellableManager,
                    closure: TextFieldViewModelImpl.() -> Unit = {}
                ) =
                    TextFieldViewModelImpl(cancellableManager)
                        .apply { placeholder = placeholderText }
                        .apply(closure)
            }
        }

        class Toggle {
            companion object {
                fun empty(
                    cancellableManager: CancellableManager,
                    closure: ToggleViewModelImpl<NoContent>.() -> Unit = {}
                ) =
                    ToggleViewModelImpl(cancellableManager, NoContent())
                        .apply(closure)

                fun withState(
                    state: Boolean,
                    cancellableManager: CancellableManager,
                    closure: ToggleViewModelImpl<NoContent>.() -> Unit = {}
                ) =
                    ToggleViewModelImpl(cancellableManager, NoContent())
                        .apply { isOn = state }
                        .apply(closure)

                fun withText(
                    text: String,
                    state: Boolean,
                    cancellableManager: CancellableManager,
                    closure: ToggleViewModelImpl<TextContent>.() -> Unit = {}
                ) =
                    ToggleViewModelImpl(cancellableManager, TextContent(text))
                        .apply { isOn = state }
                        .apply(closure)

                fun withImage(
                    image: ImageResource,
                    state: Boolean,
                    cancellableManager: CancellableManager,
                    closure: ToggleViewModelImpl<ImageContent>.() -> Unit = {}
                ) =
                    ToggleViewModelImpl(cancellableManager, ImageContent(image))
                        .apply { isOn = state }
                        .apply(closure)
            }
        }

        class Loading {
            companion object {
                fun inactive(
                    cancellableManager: CancellableManager,
                    closure: LoadingViewModelImpl.() -> Unit = {}
                ) =
                    LoadingViewModelImpl(cancellableManager)
                        .apply(closure)

                fun active(
                    cancellableManager: CancellableManager,
                    closure: LoadingViewModelImpl.() -> Unit = {}
                ) =
                    LoadingViewModelImpl(cancellableManager)
                        .apply { isLoading = true }
                        .apply(closure)
            }
        }

        class Progress {
            companion object {
                fun indeterminate(
                    cancellableManager: CancellableManager,
                    closure: ProgressViewModelImpl.() -> Unit = {}
                ) =
                    ProgressViewModelImpl(cancellableManager)
                        .apply(closure)

                fun determinate(
                    progress: Float,
                    cancellableManager: CancellableManager,
                    closure: ProgressViewModelImpl.() -> Unit = {}
                ) =
                    ProgressViewModelImpl(cancellableManager)
                        .apply { determination = ProgressDetermination(progress, 1f) }
                        .apply(closure)

                fun determinate(
                    progress: Float,
                    total: Float,
                    cancellableManager: CancellableManager,
                    closure: ProgressViewModelImpl.() -> Unit = {}
                ) =
                    ProgressViewModelImpl(cancellableManager)
                        .apply { determination = ProgressDetermination(progress, total) }
                        .apply(closure)
            }
        }

        class List {
            companion object {
                fun <C : IdentifiableContent> empty(
                    cancellableManager: CancellableManager,
                    closure: ListViewModelImpl<C>.() -> Unit = {}
                ) =
                    ListViewModelImpl<C>(cancellableManager)
                        .apply(closure)
            }
        }
    }
}
