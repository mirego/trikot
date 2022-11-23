package com.mirego.sample.viewmodels.showcase.animation.types

import com.mirego.sample.KWordTranslation
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.viewmodel.text
import kotlinx.coroutines.CoroutineScope

class AnimationTypesShowcaseViewModelImpl(i18N: I18N, coroutineScope: CoroutineScope) : ShowcaseViewModelImpl(coroutineScope), AnimationTypesShowcaseViewModel {
    override val title = text(i18N[KWordTranslation.ANIMATION_TYPES_SHOWCASE_TITLE])

    override val linear = AnimationTypeShowcaseViewModelImpl(
        title = i18N[KWordTranslation.ANIMATION_TYPES_LINEAR_TILE],
        buttonTitle = i18N[KWordTranslation.ANIMATION_TYPES_ANIMATE_BUTTON_TEXT],
        coroutineScope = coroutineScope
    )

    override val easeIn = AnimationTypeShowcaseViewModelImpl(
        title = i18N[KWordTranslation.ANIMATION_TYPES_EASE_IN_TILE],
        buttonTitle = i18N[KWordTranslation.ANIMATION_TYPES_ANIMATE_BUTTON_TEXT],
        coroutineScope = coroutineScope
    )

    override val easeOut = AnimationTypeShowcaseViewModelImpl(
        title = i18N[KWordTranslation.ANIMATION_TYPES_EASE_OUT_TILE],
        buttonTitle = i18N[KWordTranslation.ANIMATION_TYPES_ANIMATE_BUTTON_TEXT],
        coroutineScope = coroutineScope
    )

    override val easeInEaseOut = AnimationTypeShowcaseViewModelImpl(
        title = i18N[KWordTranslation.ANIMATION_TYPES_EASE_IN_EASE_OUT_TILE],
        buttonTitle = i18N[KWordTranslation.ANIMATION_TYPES_ANIMATE_BUTTON_TEXT],
        coroutineScope = coroutineScope
    )

    override val cubicBezier = AnimationTypeShowcaseViewModelImpl(
        title = i18N[KWordTranslation.ANIMATION_TYPES_CUBIC_BEZIER_TILE],
        buttonTitle = i18N[KWordTranslation.ANIMATION_TYPES_ANIMATE_BUTTON_TEXT],
        coroutineScope = coroutineScope
    )

    override val spring = AnimationTypeShowcaseViewModelImpl(
        title = i18N[KWordTranslation.ANIMATION_TYPES_SPRING_TILE],
        buttonTitle = i18N[KWordTranslation.ANIMATION_TYPES_ANIMATE_BUTTON_TEXT],
        coroutineScope = coroutineScope
    )
}
