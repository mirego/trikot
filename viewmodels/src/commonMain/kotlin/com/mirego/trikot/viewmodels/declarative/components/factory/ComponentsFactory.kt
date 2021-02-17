package com.mirego.trikot.viewmodels.declarative.components.factory

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.ImageViewModel
import com.mirego.trikot.viewmodels.declarative.components.TextViewModel
import com.mirego.trikot.viewmodels.declarative.components.mutable.MutableButtonViewModel
import com.mirego.trikot.viewmodels.declarative.components.mutable.MutableImageViewModel
import com.mirego.trikot.viewmodels.declarative.components.mutable.MutableListViewModel
import com.mirego.trikot.viewmodels.declarative.components.mutable.MutableLoadingViewModel
import com.mirego.trikot.viewmodels.declarative.components.mutable.MutableProgressViewModel
import com.mirego.trikot.viewmodels.declarative.components.mutable.MutableTextFieldViewModel
import com.mirego.trikot.viewmodels.declarative.components.mutable.MutableTextViewModel
import com.mirego.trikot.viewmodels.declarative.components.mutable.MutableToggleViewModel
import com.mirego.trikot.viewmodels.declarative.components.wrappers.wrapInConcreteClass
import com.mirego.trikot.viewmodels.declarative.properties.IdentifiableContent
import com.mirego.trikot.viewmodels.declarative.properties.ImageDescriptor
import com.mirego.trikot.viewmodels.declarative.properties.ImageResource
import com.mirego.trikot.viewmodels.declarative.properties.NoContent
import com.mirego.trikot.viewmodels.declarative.properties.ProgressDetermination
import com.mirego.trikot.viewmodels.declarative.properties.TextImagePair
import com.mirego.trikot.viewmodels.declarative.properties.TextPair

class Text {
    companion object {
        fun empty(
            cancellableManager: CancellableManager,
            closure: MutableTextViewModel.() -> Unit
        ) =
            MutableTextViewModel(cancellableManager)
                .apply(closure)

        fun withContent(
            content: String,
            cancellableManager: CancellableManager,
            closure: MutableTextViewModel.() -> Unit
        ) =
            MutableTextViewModel(cancellableManager)
                .apply { text = content }
                .apply(closure)
    }
}

class Image {
    companion object {
        fun empty(
            cancellableManager: CancellableManager,
            closure: MutableImageViewModel.() -> Unit
        ) =
            MutableImageViewModel(cancellableManager)
                .apply(closure)

        fun local(
            imageResource: ImageResource,
            cancellableManager: CancellableManager,
            closure: MutableImageViewModel.() -> Unit
        ) =
            MutableImageViewModel(cancellableManager)
                .apply { image = ImageDescriptor.Local(imageResource) }
                .apply(closure)

        fun remote(
            imageURL: String,
            placeholderImageResource: ImageResource = ImageResource.None,
            cancellableManager: CancellableManager,
            closure: MutableImageViewModel.() -> Unit
        ) =
            MutableImageViewModel(cancellableManager)
                .apply { image = ImageDescriptor.Remote(imageURL, placeholderImageResource) }
                .apply(closure)
    }
}

class Button {
    companion object {
        fun empty(
            cancellableManager: CancellableManager,
            closure: MutableButtonViewModel<NoContent>.() -> Unit
        ) =
            MutableButtonViewModel(cancellableManager, NoContent())
                .apply(closure)
                .wrapInConcreteClass()

        fun withText(
            text: TextViewModel,
            cancellableManager: CancellableManager,
            closure: MutableButtonViewModel<TextViewModel>.() -> Unit
        ) =
            MutableButtonViewModel(cancellableManager, text)
                .apply(closure)
                .wrapInConcreteClass()

        fun withImage(
            image: ImageViewModel,
            cancellableManager: CancellableManager,
            closure: MutableButtonViewModel<ImageViewModel>.() -> Unit
        ) =
            MutableButtonViewModel(cancellableManager, image)
                .apply(closure)
                .wrapInConcreteClass()

        fun withTextPair(
            firstText: TextViewModel,
            secondText: TextViewModel,
            cancellableManager: CancellableManager,
            closure: MutableButtonViewModel<TextPair>.() -> Unit
        ) =
            MutableButtonViewModel(cancellableManager, TextPair(firstText, secondText))
                .apply(closure)
                .wrapInConcreteClass()

        fun withTextImage(
            text: TextViewModel,
            image: ImageViewModel,
            cancellableManager: CancellableManager,
            closure: MutableButtonViewModel<TextImagePair>.() -> Unit
        ) =
            MutableButtonViewModel(cancellableManager, TextImagePair(text, image))
                .apply(closure)
                .wrapInConcreteClass()
    }
}

class TextField {
    companion object {
        fun empty(
            cancellableManager: CancellableManager,
            closure: MutableTextFieldViewModel.() -> Unit
        ) =
            MutableTextFieldViewModel(cancellableManager)
                .apply(closure)

        fun withPlaceholder(
            placeholderText: String,
            cancellableManager: CancellableManager,
            closure: MutableTextFieldViewModel.() -> Unit
        ) =
            MutableTextFieldViewModel(cancellableManager)
                .apply { placeholder = placeholderText }
                .apply(closure)
    }
}

class Toggle {
    companion object {
        fun empty(
            cancellableManager: CancellableManager,
            closure: MutableToggleViewModel.() -> Unit
        ) =
            MutableToggleViewModel(cancellableManager)
                .apply(closure)

        fun withState(
            state: Boolean,
            cancellableManager: CancellableManager,
            closure: MutableToggleViewModel.() -> Unit
        ) =
            MutableToggleViewModel(cancellableManager)
                .apply { isOn = state }
                .apply(closure)
    }
}

class Loading {
    companion object {
        fun inactive(
            cancellableManager: CancellableManager,
            closure: MutableLoadingViewModel.() -> Unit
        ) =
            MutableLoadingViewModel(cancellableManager)
                .apply(closure)

        fun active(
            cancellableManager: CancellableManager,
            closure: MutableLoadingViewModel.() -> Unit
        ) =
            MutableLoadingViewModel(cancellableManager)
                .apply { isLoading = true }
                .apply(closure)
    }
}

class Progress {
    companion object {
        fun indeterminate(
            cancellableManager: CancellableManager,
            closure: MutableProgressViewModel.() -> Unit
        ) =
            MutableProgressViewModel(cancellableManager)
                .apply(closure)

        fun determinate(
            progress: Float,
            cancellableManager: CancellableManager,
            closure: MutableProgressViewModel.() -> Unit
        ) =
            MutableProgressViewModel(cancellableManager)
                .apply { determination = ProgressDetermination(progress, 1f) }
                .apply(closure)

        fun determinate(
            progress: Float,
            total: Float,
            cancellableManager: CancellableManager,
            closure: MutableProgressViewModel.() -> Unit
        ) =
            MutableProgressViewModel(cancellableManager)
                .apply { determination = ProgressDetermination(progress, total) }
                .apply(closure)
    }
}

class List {
    companion object {
        fun <C : IdentifiableContent> empty(
            cancellableManager: CancellableManager,
            closure: MutableListViewModel<C>.() -> Unit
        ) =
            MutableListViewModel<C>(cancellableManager)
                .apply(closure)
    }
}
