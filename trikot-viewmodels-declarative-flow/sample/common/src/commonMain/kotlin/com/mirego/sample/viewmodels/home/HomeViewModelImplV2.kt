package com.mirego.sample.viewmodels.home

import com.mirego.sample.KWordTranslation
import com.mirego.sample.navigation.DialogInput
import com.mirego.sample.navigation.NavigationDestination
import com.mirego.sample.navigation.TextShowcaseInput
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.components.VMDListViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDSnackbarDuration
import com.mirego.trikot.viewmodels.declarative.components.VMDSnackbarViewData
import com.mirego.trikot.viewmodels.declarative.extension.VMDFlow
import com.mirego.trikot.viewmodels.declarative.extension.wrap
import com.mirego.trikot.viewmodels.declarative.navigation.VMDNavigationController
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.list
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class HomeViewModelImplV2(
    val viewModelScope: CoroutineScope,
    private val i18N: I18N,
    private val navigationController: VMDNavigationController
) : VMDViewModelImpl(viewModelScope), HomeViewModel {

    override val title = i18N[KWordTranslation.HOME_TITLE]

    override val sections: VMDListViewModel<HomeSectionViewModel> =
        list(
            HomeSectionViewModelImpl(
                text = i18N[KWordTranslation.HOME_COMPONENTS_TITLE],
                elements = listOf(
                    HomeSectionElementViewModelImpl(
                        text = i18N[KWordTranslation.HOME_COMPONENT_TEXT],
                        coroutineScope = coroutineScope
                    ) {
                        setAction {
                            navigationController.push(
                                NavigationDestination.TextShowcase(
                                    input = TextShowcaseInput(
                                        textInput = "Text as input"
                                    )
                                )
                            )
                        }
                    },
                    HomeSectionElementViewModelImpl(
                        text = i18N[KWordTranslation.HOME_COMPONENT_BUTTON],
                        coroutineScope = coroutineScope
                    ) {
//                        setAction { navigationDelegate?.navigateToButtonShowcase() }
                    },
                    HomeSectionElementViewModelImpl(
                        text = i18N[KWordTranslation.HOME_COMPONENT_IMAGE],
                        coroutineScope = coroutineScope
                    ) {
//                        setAction { navigationDelegate?.navigateToImageShowcase() }
                    },
                    HomeSectionElementViewModelImpl(
                        text = i18N[KWordTranslation.HOME_COMPONENT_TEXTFIELD],
                        coroutineScope = coroutineScope
                    ) {
//                        setAction { navigationDelegate?.navigateToTextFieldShowcase() }
                    },
                    HomeSectionElementViewModelImpl(
                        text = i18N[KWordTranslation.HOME_COMPONENT_TOGGLE],
                        coroutineScope = coroutineScope
                    ) {
//                        setAction { navigationDelegate?.navigateToToggleShowcase() }
                    },
                    HomeSectionElementViewModelImpl(
                        text = i18N[KWordTranslation.HOME_COMPONENT_PROGRESS],
                        coroutineScope = coroutineScope
                    ) {
//                        setAction { navigationDelegate?.navigateToProgressShowcase() }
                    },
                    HomeSectionElementViewModelImpl(
                        text = i18N[KWordTranslation.HOME_COMPONENT_LIST],
                        coroutineScope = coroutineScope
                    ) {
//                        setAction { navigationDelegate?.navigateToListShowcase() }
                    },
                    HomeSectionElementViewModelImpl(
                        text = i18N[KWordTranslation.HOME_COMPONENT_PICKER],
                        coroutineScope = coroutineScope
                    ) {
//                        setAction { navigationDelegate?.navigateToPickerShowcase() }
                    },
                    HomeSectionElementViewModelImpl(
                        text = i18N[KWordTranslation.HOME_COMPONENT_SNACKBAR],
                        coroutineScope = coroutineScope
                    ) {
//                        setAction { navigationDelegate?.navigateToSnackbarShowcase() }
                    }
                ),
                coroutineScope = coroutineScope
            ),
            HomeSectionViewModelImpl(
                text = i18N[KWordTranslation.HOME_ANIMATIONS_TITLE],
                elements = listOf(
                    HomeSectionElementViewModelImpl(
                        text = i18N[KWordTranslation.HOME_ANIMATION_EASING],
                        coroutineScope = coroutineScope
                    ) {
//                        setAction { navigationDelegate?.navigateToAnimationTypesShowcase() }
                    }
                ),
                coroutineScope = coroutineScope
            ),
            HomeSectionViewModelImpl(
                text = i18N[KWordTranslation.HOME_NAVIGATION_TITLE],
                elements = listOf(
                    HomeSectionElementViewModelImpl(
                        text = i18N[KWordTranslation.HOME_NAVIGATION_DIALOG],
                        coroutineScope = coroutineScope
                    ) {
                        setAction {
                            openDialog()
                        }
                    }
                ),
                coroutineScope = coroutineScope
            )
        )

    private fun openDialog() {
        navigationController.push(
            navigationItem = NavigationDestination.DialogShowcase(
                input = DialogInput(
                    message = i18N[KWordTranslation.NAVIGATION_DIALOG_MESSAGE],
                    choice1 = i18N[KWordTranslation.NAVIGATION_DIALOG_CHOICE1],
                    choice2 = i18N[KWordTranslation.NAVIGATION_DIALOG_CHOICE2],
                ),
                resultCallback = { dialogResult ->
                    coroutineScope.launch {
                        internalSnackBar.emit(
                            VMDSnackbarViewData(
                                message = when (dialogResult.choice) {
                                    i18N[KWordTranslation.NAVIGATION_DIALOG_CHOICE1] -> i18N[KWordTranslation.NAVIGATION_DIALOG_RESULT1]
                                    else -> i18N[KWordTranslation.NAVIGATION_DIALOG_RESULT2]
                                },
                                duration = VMDSnackbarDuration.LONG
                            )
                        )
                    }
                }
            )
        )
    }

    private val internalSnackBar: MutableSharedFlow<VMDSnackbarViewData> = MutableSharedFlow(0, 0)
    override val snackbar: VMDFlow<VMDSnackbarViewData> = internalSnackBar.wrap()
}
