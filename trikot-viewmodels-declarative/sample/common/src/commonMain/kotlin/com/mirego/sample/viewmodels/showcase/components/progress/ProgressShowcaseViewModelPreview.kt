package com.mirego.sample.viewmodels.showcase.components.progress

import com.mirego.sample.resources.SampleImageResource
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl

class ProgressShowcaseViewModelPreview : VMDViewModelImpl(CancellableManager()), ProgressShowcaseViewModel {
    override val title = VMDComponents.Text.withContent("Progress showcase", cancellableManager)
    override val closeButton = VMDComponents.Button.withImage(SampleImageResource.ICON_CLOSE, cancellableManager)

    override val linearDeterminateProgressTitle = VMDComponents.Text.withContent("Linear determinate", cancellableManager)
    override val linearIndeterminateProgressTitle = VMDComponents.Text.withContent("Linear indeterminate", cancellableManager)
    override val circularDeterminateProgressTitle = VMDComponents.Text.withContent("Circular determinate", cancellableManager)
    override val circularIndeterminateProgressTitle = VMDComponents.Text.withContent("Circular indeterminate", cancellableManager)

    override val determinateProgress = VMDComponents.Progress.determinate(0.25f, cancellableManager)
    override val indeterminateProgress = VMDComponents.Progress.indeterminate(cancellableManager)
}
