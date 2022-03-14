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

        viewModel.linear.animateButton.setAction {
            withAnimation(VMDAnimation.Tween(duration = 1.seconds, easing = VMDAnimationEasing.Standard.Linear)) {
                viewModel.linear.isTrailing = !viewModel.linear.isTrailing
            }
        }
        viewModel.easeIn.animateButton.setAction {
            withAnimation(VMDAnimation.Tween(duration = 1.seconds, easing = VMDAnimationEasing.Standard.EaseIn)) {
                viewModel.easeIn.isTrailing = !viewModel.easeIn.isTrailing
            }
        }
        viewModel.easeOut.animateButton.setAction {
            withAnimation(VMDAnimation.Tween(duration = 1.seconds, easing = VMDAnimationEasing.Standard.EaseOut)) {
                viewModel.easeOut.isTrailing = !viewModel.easeOut.isTrailing
            }
        }
        viewModel.easeInEaseOut.animateButton.setAction {
            withAnimation(VMDAnimation.Tween(duration = 1.seconds, easing = VMDAnimationEasing.Standard.EaseInEaseOut)) {
                viewModel.easeInEaseOut.isTrailing = !viewModel.easeInEaseOut.isTrailing
            }
        }
        viewModel.cubicBezier.animateButton.setAction {
            withAnimation(VMDAnimation.Tween(duration = 1.seconds, easing = VMDAnimationEasing.CubicBezier(0f, 1f, 0f, 1f))) {
                viewModel.cubicBezier.isTrailing = !viewModel.cubicBezier.isTrailing
            }
        }
        viewModel.spring.animateButton.setAction {
            withAnimation(VMDAnimation.Spring(dampingRatio = VMDAnimation.Spring.DampingRatioMediumBouncy, stiffness = VMDAnimation.Spring.StiffnessVeryLow)) {
                viewModel.spring.isTrailing = !viewModel.spring.isTrailing
            }
        }
    }
}