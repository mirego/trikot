package com.mirego.sample.viewmodels.showcase.progress

import com.mirego.sample.KWordTranslation
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.VMDProgressViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponentsFactory

class ProgressShowcaseViewModelImpl(i18N: I18N, cancellableManager: CancellableManager) : ShowcaseViewModelImpl(cancellableManager), ProgressShowcaseViewModel {
    override val title: VMDTextViewModel = VMDComponentsFactory.Companion.Text.withContent(i18N[KWordTranslation.PROGRESS_SHOWCASE_TITLE], cancellableManager)
    override val linearDeterminateProgressTitle: VMDTextViewModel = VMDComponentsFactory.Companion.Text.withContent(i18N[KWordTranslation.PROGRESS_SHOWCASE_LINEAR_DETERMINATE_TITLE], cancellableManager)
    override val linearIndeterminateProgressTitle: VMDTextViewModel = VMDComponentsFactory.Companion.Text.withContent(i18N[KWordTranslation.PROGRESS_SHOWCASE_LINEAR_INDETERMINATE_TITLE], cancellableManager)
    override val circularDeterminateProgressTitle: VMDTextViewModel = VMDComponentsFactory.Companion.Text.withContent(i18N[KWordTranslation.PROGRESS_SHOWCASE_CIRCULAR_DETERMINATE_TITLE], cancellableManager)
    override val circularIndeterminateProgressTitle: VMDTextViewModel = VMDComponentsFactory.Companion.Text.withContent(i18N[KWordTranslation.PROGRESS_SHOWCASE_CIRCULAR_INDETERMINATE_TITLE], cancellableManager)
    override val determinateProgress: VMDProgressViewModel = VMDComponentsFactory.Companion.Progress.determinate(0.25f, cancellableManager)
    override val indeterminateProgress: VMDProgressViewModel = VMDComponentsFactory.Companion.Progress.indeterminate(cancellableManager)
}
