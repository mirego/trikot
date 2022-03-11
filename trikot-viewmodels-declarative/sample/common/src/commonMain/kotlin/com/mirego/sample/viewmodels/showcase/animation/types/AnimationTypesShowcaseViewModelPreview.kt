package com.mirego.sample.viewmodels.showcase.animation.types

import com.mirego.sample.resources.SampleImageResource
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl

class AnimationTypesShowcaseViewModelPreview : VMDViewModelImpl(CancellableManager()), AnimationTypesShowcaseViewModel {
    override val title = VMDComponents.Text.withContent("Animation types", cancellableManager)
    override val closeButton = VMDComponents.Button.withImage(SampleImageResource.ICON_CLOSE, cancellableManager)

    override val linearTitle = VMDComponents.Text.withContent("Linear", cancellableManager)
    override val linearIsTrailing = false
    override val linearAnimateButton = VMDComponents.Button.withText("Animate", cancellableManager)

    override val easeInTitle = VMDComponents.Text.withContent("Ease In", cancellableManager)
    override val easeInIsTrailing = false
    override val easeInAnimateButton = VMDComponents.Button.withText("Animate", cancellableManager)

    override val easeOutTitle = VMDComponents.Text.withContent("Ease Out", cancellableManager)
    override val easeOutIsTrailing = false
    override val easeOutAnimateButton = VMDComponents.Button.withText("Animate", cancellableManager)

    override val easeInEaseOutTitle = VMDComponents.Text.withContent("Ease In/Out", cancellableManager)
    override val easeInEaseOutIsTrailing = false
    override val easeInEaseOutAnimateButton = VMDComponents.Button.withText("Animate", cancellableManager)

    override val cubicBezierTitle = VMDComponents.Text.withContent("Cubic Bezier", cancellableManager)
    override val cubicBezierIsTrailing = false
    override val cubicBezierAnimateButton = VMDComponents.Button.withText("Animate", cancellableManager)

    override val springTitle = VMDComponents.Text.withContent("Spring", cancellableManager)
    override val springIsTrailing = false
    override val springAnimateButton = VMDComponents.Button.withText("Animate", cancellableManager)
}
