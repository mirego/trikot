package com.mirego.sample.viewmodels.showcase.animation.types

import com.mirego.sample.KWordTranslation
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDPublishedProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.published

class AnimationTypesShowcaseViewModelImpl(i18N: I18N, cancellableManager: CancellableManager) : ShowcaseViewModelImpl(cancellableManager), AnimationTypesShowcaseViewModel {
    override val title = VMDComponents.Text.withContent(i18N[KWordTranslation.ANIMATION_TYPES_SHOWCASE_TITLE], cancellableManager)

    override val linearTitle = VMDComponents.Text.withContent(i18N[KWordTranslation.ANIMATION_TYPES_LINEAR_TILE], cancellableManager)
    private val linearIsTrailingDelegate = published(false, this)
    override var linearIsTrailing: Boolean by linearIsTrailingDelegate
    override val linearAnimateButton = VMDComponents.Button.withText(i18N[KWordTranslation.ANIMATION_TYPES_ANIMATE_BUTTON_TEXT], cancellableManager)

    override val easeInTitle = VMDComponents.Text.withContent(i18N[KWordTranslation.ANIMATION_TYPES_EASE_IN_TILE], cancellableManager)
    private val easeInIsTrailingDelegate = published(false, this)
    override var easeInIsTrailing: Boolean by easeInIsTrailingDelegate
    override val easeInAnimateButton = VMDComponents.Button.withText(i18N[KWordTranslation.ANIMATION_TYPES_ANIMATE_BUTTON_TEXT], cancellableManager)

    override val easeOutTitle = VMDComponents.Text.withContent(i18N[KWordTranslation.ANIMATION_TYPES_EASE_OUT_TILE], cancellableManager)
    private val easeOutIsTrailingDelegate = published(false, this)
    override var easeOutIsTrailing: Boolean by easeOutIsTrailingDelegate
    override val easeOutAnimateButton = VMDComponents.Button.withText(i18N[KWordTranslation.ANIMATION_TYPES_ANIMATE_BUTTON_TEXT], cancellableManager)

    override val easeInEaseOutTitle = VMDComponents.Text.withContent(i18N[KWordTranslation.ANIMATION_TYPES_EASE_IN_EASE_OUT_TILE], cancellableManager)
    private val easeInEaseOutIsTrailingDelegate = published(false, this)
    override var easeInEaseOutIsTrailing: Boolean by easeInEaseOutIsTrailingDelegate
    override val easeInEaseOutAnimateButton = VMDComponents.Button.withText(i18N[KWordTranslation.ANIMATION_TYPES_ANIMATE_BUTTON_TEXT], cancellableManager)

    override val cubicBezierTitle = VMDComponents.Text.withContent(i18N[KWordTranslation.ANIMATION_TYPES_CUBIC_BEZIER_TILE], cancellableManager)
    private val cubicBezierIsTrailingDelegate = published(false, this)
    override var cubicBezierIsTrailing: Boolean by cubicBezierIsTrailingDelegate
    override val cubicBezierAnimateButton = VMDComponents.Button.withText(i18N[KWordTranslation.ANIMATION_TYPES_ANIMATE_BUTTON_TEXT], cancellableManager)

    override val springTitle = VMDComponents.Text.withContent(i18N[KWordTranslation.ANIMATION_TYPES_SPRING_TILE], cancellableManager)
    private val springIsTrailingDelegate = published(false, this)
    override var springIsTrailing: Boolean by springIsTrailingDelegate
    override val springAnimateButton = VMDComponents.Button.withText(i18N[KWordTranslation.ANIMATION_TYPES_ANIMATE_BUTTON_TEXT], cancellableManager)

    override val propertyMapping: Map<String, VMDPublishedProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::linearIsTrailing.name] = linearIsTrailingDelegate
            it[this::easeInIsTrailing.name] = easeInIsTrailingDelegate
            it[this::easeOutIsTrailing.name] = easeOutIsTrailingDelegate
            it[this::easeInEaseOutIsTrailing.name] = easeInEaseOutIsTrailingDelegate
            it[this::cubicBezierIsTrailing.name] = cubicBezierIsTrailingDelegate
            it[this::springIsTrailing.name] = springIsTrailingDelegate
        }
    }
}