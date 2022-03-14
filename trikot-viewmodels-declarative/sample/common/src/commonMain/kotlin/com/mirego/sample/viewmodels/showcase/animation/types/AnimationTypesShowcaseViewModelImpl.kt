package com.mirego.sample.viewmodels.showcase.animation.types

import com.mirego.sample.KWordTranslation
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents

class AnimationTypesShowcaseViewModelImpl(i18N: I18N, cancellableManager: CancellableManager) : ShowcaseViewModelImpl(cancellableManager), AnimationTypesShowcaseViewModel {
    override val title = VMDComponents.Text.withContent(i18N[KWordTranslation.ANIMATION_TYPES_SHOWCASE_TITLE], cancellableManager)

    override val linear = AnimationTypeShowcaseViewModelImpl(
        title = i18N[KWordTranslation.ANIMATION_TYPES_LINEAR_TILE],
        buttonTitle = i18N[KWordTranslation.ANIMATION_TYPES_ANIMATE_BUTTON_TEXT],
        cancellableManager = cancellableManager
    )

    override val easeIn = AnimationTypeShowcaseViewModelImpl(
        title = i18N[KWordTranslation.ANIMATION_TYPES_EASE_IN_TILE],
        buttonTitle = i18N[KWordTranslation.ANIMATION_TYPES_ANIMATE_BUTTON_TEXT],
        cancellableManager = cancellableManager
    )

    override val easeOut = AnimationTypeShowcaseViewModelImpl(
        title = i18N[KWordTranslation.ANIMATION_TYPES_EASE_OUT_TILE],
        buttonTitle = i18N[KWordTranslation.ANIMATION_TYPES_ANIMATE_BUTTON_TEXT],
        cancellableManager = cancellableManager
    )

    override val easeInEaseOut = AnimationTypeShowcaseViewModelImpl(
        title = i18N[KWordTranslation.ANIMATION_TYPES_EASE_IN_EASE_OUT_TILE],
        buttonTitle = i18N[KWordTranslation.ANIMATION_TYPES_ANIMATE_BUTTON_TEXT],
        cancellableManager = cancellableManager
    )

    override val cubicBezier = AnimationTypeShowcaseViewModelImpl(
        title = i18N[KWordTranslation.ANIMATION_TYPES_CUBIC_BEZIER_TILE],
        buttonTitle = i18N[KWordTranslation.ANIMATION_TYPES_ANIMATE_BUTTON_TEXT],
        cancellableManager = cancellableManager
    )

    override val spring = AnimationTypeShowcaseViewModelImpl(
        title = i18N[KWordTranslation.ANIMATION_TYPES_SPRING_TILE],
        buttonTitle = i18N[KWordTranslation.ANIMATION_TYPES_ANIMATE_BUTTON_TEXT],
        cancellableManager = cancellableManager
    )
}