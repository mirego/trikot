package com.mirego.sample.viewmodels.showcase.animation.types

import com.mirego.sample.viewmodels.showcase.ShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDButtonViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent

interface AnimationTypesShowcaseViewModel : ShowcaseViewModel {
    val linearTitle: VMDTextViewModel
    val linearIsTrailing: Boolean
    val linearAnimateButton: VMDButtonViewModel<VMDTextContent>

    val easeInTitle: VMDTextViewModel
    val easeInIsTrailing: Boolean
    val easeInAnimateButton: VMDButtonViewModel<VMDTextContent>

    val easeOutTitle: VMDTextViewModel
    val easeOutIsTrailing: Boolean
    val easeOutAnimateButton: VMDButtonViewModel<VMDTextContent>

    val easeInEaseOutTitle: VMDTextViewModel
    val easeInEaseOutIsTrailing: Boolean
    val easeInEaseOutAnimateButton: VMDButtonViewModel<VMDTextContent>

    val cubicBezierTitle: VMDTextViewModel
    val cubicBezierIsTrailing: Boolean
    val cubicBezierAnimateButton: VMDButtonViewModel<VMDTextContent>

    val springTitle: VMDTextViewModel
    val springIsTrailing: Boolean
    val springAnimateButton: VMDButtonViewModel<VMDTextContent>
}