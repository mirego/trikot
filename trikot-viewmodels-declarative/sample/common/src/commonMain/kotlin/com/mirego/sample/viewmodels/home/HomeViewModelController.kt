package com.mirego.sample.viewmodels.home

import com.mirego.sample.KWordTranslation
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.components.VMDListViewModel
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.controller.VMDViewModelController
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl

class HomeViewModelController(i18N: I18N) :
    VMDViewModelController<HomeViewModel, HomeNavigationDelegate>() {

    override val viewModel: HomeViewModel =
        object : VMDViewModelImpl(cancellableManager), HomeViewModel {
            override val title = i18N[KWordTranslation.HOME_TITLE]

            override val sections: VMDListViewModel<HomeSectionViewModel> =
                VMDComponents.List.of(
                    HomeSectionViewModelImpl(
                        text = i18N[KWordTranslation.HOME_COMPONENTS_TITLE],
                        elements = listOf(
                            HomeSectionElementViewModelImpl(
                                text = i18N[KWordTranslation.HOME_COMPONENT_TEXT],
                                cancellableManager = cancellableManager
                            ) {
                                setAction { navigationDelegate?.navigateToTextShowcase() }
                            },
                            HomeSectionElementViewModelImpl(
                                text = i18N[KWordTranslation.HOME_COMPONENT_BUTTON],
                                cancellableManager = cancellableManager
                            ) {
                                setAction { navigationDelegate?.navigateToButtonShowcase() }
                            },
                            HomeSectionElementViewModelImpl(
                                text = i18N[KWordTranslation.HOME_COMPONENT_IMAGE],
                                cancellableManager = cancellableManager
                            ) {
                                setAction { navigationDelegate?.navigateToImageShowcase() }
                            },
                            HomeSectionElementViewModelImpl(
                                text = i18N[KWordTranslation.HOME_COMPONENT_TEXTFIELD],
                                cancellableManager = cancellableManager
                            ) {
                                setAction { navigationDelegate?.navigateToTextFieldShowcase() }
                            },
                            HomeSectionElementViewModelImpl(
                                text = i18N[KWordTranslation.HOME_COMPONENT_TOGGLE],
                                cancellableManager = cancellableManager
                            ) {
                                setAction { navigationDelegate?.navigateToToggleShowcase() }
                            },
                            HomeSectionElementViewModelImpl(
                                text = i18N[KWordTranslation.HOME_COMPONENT_PROGRESS],
                                cancellableManager = cancellableManager
                            ) {
                                setAction { navigationDelegate?.navigateToProgressShowcase() }
                            }
                        ),
                        cancellableManager = cancellableManager
                    ),
                    HomeSectionViewModelImpl(
                        text = i18N[KWordTranslation.HOME_ANIMATIONS_TITLE],
                        elements = listOf(
                            HomeSectionElementViewModelImpl(
                                text = i18N[KWordTranslation.HOME_ANIMATION_EASING],
                                cancellableManager = cancellableManager
                            ) {
                                setAction { navigationDelegate?.navigateToTextShowcase() }
                            }
                        ),
                        cancellableManager = cancellableManager
                    ),
                    cancellableManager = cancellableManager
                )
        }
}
