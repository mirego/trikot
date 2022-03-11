package com.mirego.sample.viewmodels.showcase.animation.types

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.animation.VMDAnimation
import com.mirego.trikot.viewmodels.declarative.animation.VMDAnimationEasing
import com.mirego.trikot.viewmodels.declarative.animation.withAnimation
import com.mirego.trikot.viewmodels.declarative.controller.VMDViewModelController
import kotlin.time.Duration.Companion.seconds

class AnimationTypesShowcaseViewModelController(i18N: I18N) : VMDViewModelController<AnimationTypesShowcaseViewModel, AnimationTypesShowcaseNavigationDelegate>() {
    override val viewModel: AnimationTypesShowcaseViewModelImpl = AnimationTypesShowcaseViewModelImpl(i18N, cancellableManager)

    override fun onCreate() {
        super.onCreate()

        viewModel.closeButton.setAction { navigationDelegate?.close() }

        viewModel.linearAnimateButton.setAction {
            withAnimation(VMDAnimation.Tween(duration = 1.seconds, easing = VMDAnimationEasing.Standard.Linear)) {
                viewModel.linearIsTrailing = !viewModel.linearIsTrailing
            }
        }
        viewModel.easeInAnimateButton.setAction {
            withAnimation(VMDAnimation.Tween(duration = 1.seconds, easing = VMDAnimationEasing.Standard.EaseIn)) {
                viewModel.easeInIsTrailing = !viewModel.easeInIsTrailing
            }
        }
        viewModel.easeOutAnimateButton.setAction {
            withAnimation(VMDAnimation.Tween(duration = 1.seconds, easing = VMDAnimationEasing.Standard.EaseOut)) {
                viewModel.easeOutIsTrailing = !viewModel.easeOutIsTrailing
            }
        }
        viewModel.easeInEaseOutAnimateButton.setAction {
            withAnimation(VMDAnimation.Tween(duration = 1.seconds, easing = VMDAnimationEasing.Standard.EaseInEaseOut)) {
                viewModel.easeInEaseOutIsTrailing = !viewModel.easeInEaseOutIsTrailing
            }
        }
        viewModel.cubicBezierAnimateButton.setAction {
            withAnimation(VMDAnimation.Tween(duration = 1.seconds, easing = VMDAnimationEasing.CubicBezier(0f, 1f, 0f, 1f))) {
                viewModel.cubicBezierIsTrailing = !viewModel.cubicBezierIsTrailing
            }
        }
        viewModel.springAnimateButton.setAction {
            withAnimation(VMDAnimation.Spring(dampingRatio = VMDAnimation.Spring.DampingRatioNoBouncy, stiffness = VMDAnimation.Spring.StiffnessVeryLow)) {
                viewModel.springIsTrailing = !viewModel.springIsTrailing
            }
        }
    }
}