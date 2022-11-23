package com.mirego.sample.viewmodels.showcase.animation.types

import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.buttonWithText
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDFlowProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.emit
import com.mirego.trikot.viewmodels.declarative.viewmodel.text
import kotlinx.coroutines.CoroutineScope

class AnimationTypeShowcaseViewModelImpl(title: String, buttonTitle: String, coroutineScope: CoroutineScope) : VMDViewModelImpl(coroutineScope), AnimationTypeShowcaseViewModel {
    override val title = text(title)

    private val isTrailingDelegate = emit(false, this, coroutineScope)
    override var isTrailing: Boolean by isTrailingDelegate

    override val animateButton = buttonWithText(buttonTitle)

    override val propertyMapping: Map<String, VMDFlowProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::isTrailing.name] = isTrailingDelegate
        }
    }
}
