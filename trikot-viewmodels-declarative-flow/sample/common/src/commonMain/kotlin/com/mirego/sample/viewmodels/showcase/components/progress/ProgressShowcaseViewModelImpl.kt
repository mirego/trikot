package com.mirego.sample.viewmodels.showcase.components.progress

import com.mirego.sample.KWordTranslation
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.components.VMDProgressViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import kotlinx.coroutines.CoroutineScope

class ProgressShowcaseViewModelImpl(i18N: I18N, coroutineScope: CoroutineScope) : ShowcaseViewModelImpl(coroutineScope), ProgressShowcaseViewModel {
    override val title: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.PROGRESS_SHOWCASE_TITLE], coroutineScope)
    override val linearDeterminateProgressTitle: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.PROGRESS_SHOWCASE_LINEAR_DETERMINATE_TITLE], coroutineScope)
    override val linearIndeterminateProgressTitle: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.PROGRESS_SHOWCASE_LINEAR_INDETERMINATE_TITLE], coroutineScope)
    override val circularDeterminateProgressTitle: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.PROGRESS_SHOWCASE_CIRCULAR_DETERMINATE_TITLE], coroutineScope)
    override val circularIndeterminateProgressTitle: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.PROGRESS_SHOWCASE_CIRCULAR_INDETERMINATE_TITLE], coroutineScope)
    override val determinateProgress: VMDProgressViewModel = VMDComponents.Progress.determinate(0.25f, coroutineScope)
    override val indeterminateProgress: VMDProgressViewModel = VMDComponents.Progress.indeterminate(coroutineScope)
}
