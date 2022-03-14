package com.mirego.sample.viewmodels.showcase.animation.types

import com.mirego.sample.viewmodels.showcase.ShowcaseViewModel

interface AnimationTypesShowcaseViewModel : ShowcaseViewModel {
    val linear: AnimationTypeShowcaseViewModel

    val easeIn: AnimationTypeShowcaseViewModel

    val easeOut: AnimationTypeShowcaseViewModel

    val easeInEaseOut: AnimationTypeShowcaseViewModel

    val cubicBezier: AnimationTypeShowcaseViewModel

    val spring: AnimationTypeShowcaseViewModel
}