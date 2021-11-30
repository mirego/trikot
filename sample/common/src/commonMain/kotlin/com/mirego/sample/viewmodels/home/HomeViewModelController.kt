package com.mirego.sample.viewmodels.home

import com.mirego.sample.KWordTranslation
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.components.VMDButtonViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDListViewModel
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponentsFactory
import com.mirego.trikot.viewmodels.declarative.components.impl.VMDTextViewModelImpl
import com.mirego.trikot.viewmodels.declarative.content.VMDIdentifiableContentAbstract
import com.mirego.trikot.viewmodels.declarative.content.VMDIdentifiableContentWrapper
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent
import com.mirego.trikot.viewmodels.declarative.controller.VMDViewModelController
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl

class HomeViewModelController(i18N: I18N) :
    VMDViewModelController<HomeViewModel, HomeNavigationDelegate>() {

    override val viewModel: HomeViewModel =
        object : VMDViewModelImpl(cancellableManager), HomeViewModel {
            override val title = VMDTextViewModelImpl(cancellableManager).apply {
                text = i18N[KWordTranslation.HOME_TITLE]
            }
            override val items: VMDListViewModel<VMDIdentifiableContentAbstract<VMDButtonViewModel<VMDTextContent>>> =
                VMDComponentsFactory.Companion.List.of(
                    VMDIdentifiableContentWrapper(
                        "text",
                        VMDComponentsFactory.Companion.Button.withText(
                            i18N[KWordTranslation.HOME_COMPONENT_TEXT],
                            cancellableManager
                        ) {
                            setAction { navigationDelegate?.navigateToTextShowcase() }
                        }
                    ),
                    VMDIdentifiableContentWrapper(
                        "button",
                        VMDComponentsFactory.Companion.Button.withText(
                            i18N[KWordTranslation.HOME_COMPONENT_BUTTON],
                            cancellableManager
                        ) {
                            setAction { navigationDelegate?.navigateToButtonShowcase() }
                        }
                    ),
                    VMDIdentifiableContentWrapper(
                        "image",
                        VMDComponentsFactory.Companion.Button.withText(
                            i18N[KWordTranslation.HOME_COMPONENT_IMAGE],
                            cancellableManager
                        ) {
                            setAction { navigationDelegate?.navigateToImageShowcase() }
                        }
                    ),
                    VMDIdentifiableContentWrapper(
                        "text_field",
                        VMDComponentsFactory.Companion.Button.withText(
                            i18N[KWordTranslation.HOME_COMPONENT_TEXTFIELD],
                            cancellableManager
                        ) {
                            setAction { navigationDelegate?.navigateToTextFieldShowcase() }
                        }
                    ),
                    VMDIdentifiableContentWrapper(
                        "toggle",
                        VMDComponentsFactory.Companion.Button.withText(
                            i18N[KWordTranslation.HOME_COMPONENT_TOGGLE],
                            cancellableManager
                        ) {
                            setAction { navigationDelegate?.navigateToToggleShowcase() }
                        }
                    ),
                    VMDIdentifiableContentWrapper(
                        "progress",
                        VMDComponentsFactory.Companion.Button.withText(
                            i18N[KWordTranslation.HOME_COMPONENT_PROGRESS],
                            cancellableManager
                        ) {
                            setAction { navigationDelegate?.navigateToProgressShowcase() }
                        }
                    ),
                    cancellableManager = cancellableManager
                )
        }
}
