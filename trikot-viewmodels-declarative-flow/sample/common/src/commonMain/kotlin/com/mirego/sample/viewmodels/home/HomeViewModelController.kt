package com.mirego.sample.viewmodels.home

import com.mirego.sample.KWordTranslation
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.components.VMDListViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDSnackbarViewData
import com.mirego.trikot.viewmodels.declarative.controller.VMDViewModelController
import com.mirego.trikot.viewmodels.declarative.extension.VMDFlow
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.list

class HomeViewModelController(i18N: I18N) :
    VMDViewModelController<HomeViewModel, HomeNavigationDelegate>() {

    override val viewModel: HomeViewModel =
        object : VMDViewModelImpl(viewModelScope), HomeViewModel {
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
                                setAction { navigationDelegate?.navigateToTextShowcase() }
                            },
                            HomeSectionElementViewModelImpl(
                                text = i18N[KWordTranslation.HOME_COMPONENT_BUTTON],
                                coroutineScope = coroutineScope
                            ) {
                                setAction { navigationDelegate?.navigateToButtonShowcase() }
                            },
                            HomeSectionElementViewModelImpl(
                                text = i18N[KWordTranslation.HOME_COMPONENT_IMAGE],
                                coroutineScope = coroutineScope
                            ) {
                                setAction { navigationDelegate?.navigateToImageShowcase() }
                            },
                            HomeSectionElementViewModelImpl(
                                text = i18N[KWordTranslation.HOME_COMPONENT_TEXTFIELD],
                                coroutineScope = coroutineScope
                            ) {
                                setAction { navigationDelegate?.navigateToTextFieldShowcase() }
                            },
                            HomeSectionElementViewModelImpl(
                                text = i18N[KWordTranslation.HOME_COMPONENT_TOGGLE],
                                coroutineScope = coroutineScope
                            ) {
                                setAction { navigationDelegate?.navigateToToggleShowcase() }
                            },
                            HomeSectionElementViewModelImpl(
                                text = i18N[KWordTranslation.HOME_COMPONENT_PROGRESS],
                                coroutineScope = coroutineScope
                            ) {
                                setAction { navigationDelegate?.navigateToProgressShowcase() }
                            },
                            HomeSectionElementViewModelImpl(
                                text = i18N[KWordTranslation.HOME_COMPONENT_LIST],
                                coroutineScope = coroutineScope
                            ) {
                                setAction { navigationDelegate?.navigateToListShowcase() }
                            },
                            HomeSectionElementViewModelImpl(
                                text = i18N[KWordTranslation.HOME_COMPONENT_PICKER],
                                coroutineScope = coroutineScope
                            ) {
                                setAction { navigationDelegate?.navigateToPickerShowcase() }
                            },
                            HomeSectionElementViewModelImpl(
                                text = i18N[KWordTranslation.HOME_COMPONENT_SNACKBAR],
                                coroutineScope = coroutineScope
                            ) {
                                setAction { navigationDelegate?.navigateToSnackbarShowcase() }
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
                                setAction { navigationDelegate?.navigateToAnimationTypesShowcase() }
                            }
                        ),
                        coroutineScope = coroutineScope
                    )
                )
            override val snackbar: VMDFlow<VMDSnackbarViewData>
                get() = TODO("Not yet implemented")
        }
}
