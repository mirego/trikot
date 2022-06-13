package com.mirego.sample.viewmodels.showcase.animation.types

import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDFlowProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.emit
import kotlinx.coroutines.CoroutineScope

class AnimationTypeShowcaseViewModelImpl(title: String, buttonTitle: String, coroutineScope: CoroutineScope) : VMDViewModelImpl(coroutineScope), AnimationTypeShowcaseViewModel {
    override val title = VMDComponents.Text.withContent(title, coroutineScope)

    private val isTrailingDelegate = emit(false, this, coroutineScope)
    override var isTrailing: Boolean by isTrailingDelegate

    override val animateButton = VMDComponents.Button.withText(buttonTitle, coroutineScope)

    override val propertyMapping: Map<String, VMDFlowProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::isTrailing.name] = isTrailingDelegate
        }
    }
}
