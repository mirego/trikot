package com.mirego.sample.viewmodels.tv

import com.mirego.sample.KWordTranslation
import com.mirego.sample.viewmodels.showcase.components.button.ButtonShowcaseViewModelImpl
import com.mirego.sample.viewmodels.showcase.components.carousel.CarouselShowcaseViewModelImpl
import com.mirego.sample.viewmodels.showcase.components.text.TextShowcaseViewModelImpl
import com.mirego.sample.viewmodels.showcase.components.toggle.ToggleShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import kotlinx.coroutines.CoroutineScope

class HomeTvViewModelImpl(
    i18N: I18N,
    coroutineScope: CoroutineScope
) : HomeTvViewModel, VMDViewModelImpl(coroutineScope) {

    override val menuItems: List<HomeMenuSectionItem> = listOf(
        HomeMenuSectionItem.TextShowcase(
            title = i18N[KWordTranslation.TEXT_SHOWCASE_TITLE],
            viewModel = TextShowcaseViewModelImpl(i18N, coroutineScope)
        ),
        HomeMenuSectionItem.ButtonShowcase(
            title = i18N[KWordTranslation.BUTTON_SHOWCASE_TITLE],
            viewModel = ButtonShowcaseViewModelImpl(i18N, coroutineScope)
        ),
        HomeMenuSectionItem.ToggleShowcase(
            title = i18N[KWordTranslation.TOGGLE_SHOWCASE_TITLE],
            viewModel = ToggleShowcaseViewModelImpl(i18N, coroutineScope)
        ),
        HomeMenuSectionItem.CarouselShowcase(
            title = i18N[KWordTranslation.CAROUSEL_SHOWCASE_TITLE],
            viewModel = CarouselShowcaseViewModelImpl(i18N, coroutineScope)
        )
    )
}
