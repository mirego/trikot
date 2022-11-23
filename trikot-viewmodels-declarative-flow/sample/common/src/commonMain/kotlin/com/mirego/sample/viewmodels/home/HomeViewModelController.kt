package com.mirego.sample.viewmodels.home

import com.mirego.sample.KWordTranslation
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.components.VMDListViewModel
import com.mirego.trikot.viewmodels.declarative.controller.VMDViewModelController
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.list

class HomeViewModelController(i18N: I18N) :
    VMDViewModelController<HomeViewModel, HomeNavigationDelegate>() {

    override val viewModel: HomeViewModel =
        object : VMDViewModelImpl(viewModelControllerScope), HomeViewModel {
            override val title = i18N[KWordTranslation.HOME_TITLE]

            override val sections: VMDListViewModel<HomeSectionViewModel> =
                list(
                    HomeSectionViewModelImpl(
                        text = i18N[KWordTranslation.HOME_COMPONENTS_TITLE],
                        elements = listOf(
                            HomeSectionElementViewModelImpl(
                                text = i18N[KWordTranslation.HOME_COMPONENT_TEXT],
                                coroutineScope = viewModelControllerScope
                            ) {
                                setAction { navigationDelegate?.navigateToTextShowcase() }
                            },
                            HomeSectionElementViewModelImpl(
                                text = i18N[KWordTranslation.HOME_COMPONENT_BUTTON],
                                coroutineScope = viewModelControllerScope
                            ) {
                                setAction { navigationDelegate?.navigateToButtonShowcase() }
                            },
                            HomeSectionElementViewModelImpl(
                                text = i18N[KWordTranslation.HOME_COMPONENT_IMAGE],
                                coroutineScope = viewModelControllerScope
                            ) {
                                setAction { navigationDelegate?.navigateToImageShowcase() }
                            },
                            HomeSectionElementViewModelImpl(
                                text = i18N[KWordTranslation.HOME_COMPONENT_TEXTFIELD],
                                coroutineScope = viewModelControllerScope
                            ) {
                                setAction { navigationDelegate?.navigateToTextFieldShowcase() }
                            },
                            HomeSectionElementViewModelImpl(
                                text = i18N[KWordTranslation.HOME_COMPONENT_TOGGLE],
                                coroutineScope = viewModelControllerScope
                            ) {
                                setAction { navigationDelegate?.navigateToToggleShowcase() }
                            },
                            HomeSectionElementViewModelImpl(
                                text = i18N[KWordTranslation.HOME_COMPONENT_PROGRESS],
                                coroutineScope = viewModelControllerScope
                            ) {
                                setAction { navigationDelegate?.navigateToProgressShowcase() }
                            },
                            HomeSectionElementViewModelImpl(
                                text = i18N[KWordTranslation.HOME_COMPONENT_LIST],
                                coroutineScope = viewModelControllerScope
                            ) {
                                setAction { navigationDelegate?.navigateToListShowcase() }
                            },
                            HomeSectionElementViewModelImpl(
                                text = i18N[KWordTranslation.HOME_COMPONENT_PICKER],
                                coroutineScope = viewModelControllerScope
                            ) {
                                setAction { navigationDelegate?.navigateToPickerShowcase() }
                            },
                            HomeSectionElementViewModelImpl(
                                text = i18N[KWordTranslation.HOME_COMPONENT_SNACKBAR],
                                coroutineScope = viewModelControllerScope
                            ) {
                                setAction { navigationDelegate?.navigateToSnackbarShowcase() }
                            }
                        ),
                        coroutineScope = viewModelControllerScope
                    ),
                    HomeSectionViewModelImpl(
                        text = i18N[KWordTranslation.HOME_ANIMATIONS_TITLE],
                        elements = listOf(
                            HomeSectionElementViewModelImpl(
                                text = i18N[KWordTranslation.HOME_ANIMATION_EASING],
                                coroutineScope = viewModelControllerScope
                            ) {
                                setAction { navigationDelegate?.navigateToAnimationTypesShowcase() }
                            }
                        ),
                        coroutineScope = viewModelControllerScope
                    )
                )
        }
}
