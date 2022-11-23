package com.mirego.sample.viewmodels.showcase.components.progress

import com.mirego.sample.resources.SampleImageResource
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.buttonWithImage
import com.mirego.trikot.viewmodels.declarative.viewmodel.determinateProgress
import com.mirego.trikot.viewmodels.declarative.viewmodel.indeterminateProgress
import com.mirego.trikot.viewmodels.declarative.viewmodel.text
import kotlinx.coroutines.MainScope

class ProgressShowcaseViewModelPreview : VMDViewModelImpl(MainScope()), ProgressShowcaseViewModel {
    override val title = text("Progress showcase")
    override val closeButton = buttonWithImage(SampleImageResource.ICON_CLOSE)

    override val linearDeterminateProgressTitle = text("Linear determinate")
    override val linearIndeterminateProgressTitle = text("Linear indeterminate")
    override val circularDeterminateProgressTitle = text("Circular determinate")
    override val circularIndeterminateProgressTitle = text("Circular indeterminate")

    override val determinateProgress = determinateProgress(0.25f)
    override val indeterminateProgress = indeterminateProgress()
}
