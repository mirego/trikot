package com.mirego.sample.viewmodels.showcase.animation.types

import com.mirego.sample.KWordTranslation
import com.mirego.sample.resources.SampleImageResource
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl

class AnimationTypesShowcaseViewModelPreview : VMDViewModelImpl(CancellableManager()), AnimationTypesShowcaseViewModel {
    override val title = VMDComponents.Text.withContent("Animation types", cancellableManager)
    override val closeButton = VMDComponents.Button.withImage(SampleImageResource.ICON_CLOSE, cancellableManager)

    override val linear = AnimationTypeShowcaseViewModelImpl(
        title = "Linear",
        buttonTitle = "Animate",
        cancellableManager = cancellableManager
    )

    override val easeIn = AnimationTypeShowcaseViewModelImpl(
        title = "Ease In",
        buttonTitle = "Animate",
        cancellableManager = cancellableManager
    )

    override val easeOut = AnimationTypeShowcaseViewModelImpl(
        title = "Ease Out",
        buttonTitle = "Animate",
        cancellableManager = cancellableManager
    )

    override val easeInEaseOut = AnimationTypeShowcaseViewModelImpl(
        title = "Ease In/Out",
        buttonTitle = "Animate",
        cancellableManager = cancellableManager
    )

    override val cubicBezier = AnimationTypeShowcaseViewModelImpl(
        title = "Cubic Bezier",
        buttonTitle = "Animate",
        cancellableManager = cancellableManager
    )

    override val spring = AnimationTypeShowcaseViewModelImpl(
        title = "Spring",
        buttonTitle = "Animate",
        cancellableManager = cancellableManager
    )
}
