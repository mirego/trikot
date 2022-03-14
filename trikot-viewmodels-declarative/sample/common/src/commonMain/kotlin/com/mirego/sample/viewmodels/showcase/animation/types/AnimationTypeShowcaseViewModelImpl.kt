package com.mirego.sample.viewmodels.showcase.animation.types

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDPublishedProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.published

class AnimationTypeShowcaseViewModelImpl(title: String, buttonTitle: String, cancellableManager: CancellableManager) : VMDViewModelImpl(cancellableManager), AnimationTypeShowcaseViewModel {
    override val title = VMDComponents.Text.withContent(title, cancellableManager)

    private val isTrailingDelegate = published(false, this)
    override var isTrailing: Boolean by isTrailingDelegate

    override val animateButton = VMDComponents.Button.withText(buttonTitle, cancellableManager)

    override val propertyMapping: Map<String, VMDPublishedProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::isTrailing.name] = isTrailingDelegate
        }
    }
}
