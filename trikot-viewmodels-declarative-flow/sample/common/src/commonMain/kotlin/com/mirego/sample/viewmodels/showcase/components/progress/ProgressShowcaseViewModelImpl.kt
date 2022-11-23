package com.mirego.sample.viewmodels.showcase.components.progress

import com.mirego.sample.KWordTranslation
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.viewmodel.determinateProgress
import com.mirego.trikot.viewmodels.declarative.viewmodel.indeterminateProgress
import com.mirego.trikot.viewmodels.declarative.viewmodel.text
import kotlinx.coroutines.CoroutineScope

class ProgressShowcaseViewModelImpl(i18N: I18N, coroutineScope: CoroutineScope) : ShowcaseViewModelImpl(coroutineScope), ProgressShowcaseViewModel {
    override val title = text(i18N[KWordTranslation.PROGRESS_SHOWCASE_TITLE])
    override val linearDeterminateProgressTitle = text(i18N[KWordTranslation.PROGRESS_SHOWCASE_LINEAR_DETERMINATE_TITLE])
    override val linearIndeterminateProgressTitle = text(i18N[KWordTranslation.PROGRESS_SHOWCASE_LINEAR_INDETERMINATE_TITLE])
    override val circularDeterminateProgressTitle = text(i18N[KWordTranslation.PROGRESS_SHOWCASE_CIRCULAR_DETERMINATE_TITLE])
    override val circularIndeterminateProgressTitle = text(i18N[KWordTranslation.PROGRESS_SHOWCASE_CIRCULAR_INDETERMINATE_TITLE])
    override val determinateProgress = determinateProgress(0.25f)
    override val indeterminateProgress = indeterminateProgress()
}
