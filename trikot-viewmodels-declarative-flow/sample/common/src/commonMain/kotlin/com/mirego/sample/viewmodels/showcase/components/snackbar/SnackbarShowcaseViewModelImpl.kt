package com.mirego.sample.viewmodels.showcase.components.snackbar

import com.mirego.sample.KWordTranslation
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.components.VMDButtonViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDSnackbarDuration
import com.mirego.trikot.viewmodels.declarative.components.VMDSnackbarViewData
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent
import com.mirego.trikot.viewmodels.declarative.extension.wrap
import com.mirego.trikot.viewmodels.declarative.viewmodel.buttonWithText
import com.mirego.trikot.viewmodels.declarative.viewmodel.text
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class SnackbarShowcaseViewModelImpl(i18N: I18N, coroutineScope: CoroutineScope) :
    ShowcaseViewModelImpl(coroutineScope), SnackbarShowcaseViewModel {
    override val title = text(i18N[KWordTranslation.SNACKBAR_SHOWCASE_TITLE])

    private val internalSnackBar: MutableSharedFlow<VMDSnackbarViewData> = MutableSharedFlow(0, 0)
    override val snackbar = internalSnackBar.wrap()

    private val short = buttonWithText(i18N[KWordTranslation.SNACKBAR_SHOWCASE_SHORT]) {
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
    private val long = buttonWithText(i18N[KWordTranslation.SNACKBAR_SHOWCASE_LONG]) {
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
    private val indefinite = buttonWithText(i18N[KWordTranslation.SNACKBAR_SHOWCASE_INDEFINITE]) {
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
