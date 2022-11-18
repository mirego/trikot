package com.mirego.sample.viewmodels.showcase.components.snackbar

import com.mirego.sample.KWordTranslation
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.components.VMDButtonViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDSnackbarDuration
import com.mirego.trikot.viewmodels.declarative.components.VMDSnackbarViewData
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent
import com.mirego.trikot.viewmodels.declarative.extension.wrap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class SnackbarShowcaseViewModelImpl(i18N: I18N, coroutineScope: CoroutineScope) :
    ShowcaseViewModelImpl(coroutineScope), SnackbarShowcaseViewModel {
    override val title = VMDComponents.Text.withContent(
        i18N[KWordTranslation.SNACKBAR_SHOWCASE_TITLE],
        coroutineScope
    )

    private val internalSnackBar: MutableSharedFlow<VMDSnackbarViewData> = MutableSharedFlow(0, 0)
    override val snackbar = internalSnackBar.wrap()

    private val short: VMDButtonViewModel<VMDTextContent> = VMDComponents.Button.withText(i18N[KWordTranslation.SNACKBAR_SHOWCASE_SHORT], coroutineScope) {
        setAction {
            coroutineScope.launch {
                internalSnackBar.emit(
                    VMDSnackbarViewData(
                        message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                        duration = VMDSnackbarDuration.SHORT,
                        withDismissAction = false
                    )
                )
            }
        }
    }
    private val long: VMDButtonViewModel<VMDTextContent> = VMDComponents.Button.withText(i18N[KWordTranslation.SNACKBAR_SHOWCASE_LONG], coroutineScope) {
        setAction {
            coroutineScope.launch {
                internalSnackBar.emit(
                    VMDSnackbarViewData(
                        message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                        duration = VMDSnackbarDuration.LONG
                    )
                )
            }
        }
    }
    private val indefinite: VMDButtonViewModel<VMDTextContent> = VMDComponents.Button.withText(i18N[KWordTranslation.SNACKBAR_SHOWCASE_INDEFINITE], coroutineScope) {
        setAction {
            coroutineScope.launch {
                internalSnackBar.emit(
                    VMDSnackbarViewData(
                        message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                        duration = VMDSnackbarDuration.INDEFINITE
                    )
                )
            }
        }
    }

    override val buttons: List<VMDButtonViewModel<VMDTextContent>> = listOf(short, long, indefinite)
}
