package com.mirego.sample.viewmodels.showcase.animation.types

import com.mirego.sample.resources.SampleImageResource
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.buttonWithImage
import com.mirego.trikot.viewmodels.declarative.viewmodel.text
import kotlinx.coroutines.MainScope

class AnimationTypesShowcaseViewModelPreview : VMDViewModelImpl(MainScope()), AnimationTypesShowcaseViewModel {
    override val title = text("Animation types")
    override val closeButton = buttonWithImage(SampleImageResource.ICON_CLOSE)

    override val linear = AnimationTypeShowcaseViewModelImpl(
        title = "Linear",
        buttonTitle = "Animate",
        coroutineScope = coroutineScope
    )

    override val easeIn = AnimationTypeShowcaseViewModelImpl(
        title = "Ease In",
        buttonTitle = "Animate",
        coroutineScope = coroutineScope
    )

    override val easeOut = AnimationTypeShowcaseViewModelImpl(
        title = "Ease Out",
        buttonTitle = "Animate",
        coroutineScope = coroutineScope
    )

    override val easeInEaseOut = AnimationTypeShowcaseViewModelImpl(
        title = "Ease In/Out",
        buttonTitle = "Animate",
        coroutineScope = coroutineScope
    )

    override val cubicBezier = AnimationTypeShowcaseViewModelImpl(
        title = "Cubic Bezier",
        buttonTitle = "Animate",
        coroutineScope = coroutineScope
    )

    override val spring = AnimationTypeShowcaseViewModelImpl(
        title = "Spring",
        buttonTitle = "Animate",
        coroutineScope = coroutineScope
    )
}
