package com.mirego.sample.viewmodels.showcase.components.progress

import com.mirego.sample.resources.SampleImageResource
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import kotlinx.coroutines.MainScope

class ProgressShowcaseViewModelPreview : VMDViewModelImpl(MainScope()), ProgressShowcaseViewModel {
    override val title = VMDComponents.Text.withContent("Progress showcase", coroutineScope)
    override val closeButton = VMDComponents.Button.withImage(SampleImageResource.ICON_CLOSE, coroutineScope)

    override val linearDeterminateProgressTitle = VMDComponents.Text.withContent("Linear determinate", coroutineScope)
    override val linearIndeterminateProgressTitle = VMDComponents.Text.withContent("Linear indeterminate", coroutineScope)
    override val circularDeterminateProgressTitle = VMDComponents.Text.withContent("Circular determinate", coroutineScope)
    override val circularIndeterminateProgressTitle = VMDComponents.Text.withContent("Circular indeterminate", coroutineScope)

    override val determinateProgress = VMDComponents.Progress.determinate(0.25f, coroutineScope)
    override val indeterminateProgress = VMDComponents.Progress.indeterminate(coroutineScope)
}
