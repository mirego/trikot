package com.mirego.sample.viewmodels.tv

import com.mirego.sample.viewmodels.showcase.components.button.ButtonShowcaseViewModel
import com.mirego.sample.viewmodels.showcase.components.carousel.CarouselShowcaseViewModel
import com.mirego.sample.viewmodels.showcase.components.text.TextShowcaseViewModel
import com.mirego.sample.viewmodels.showcase.components.toggle.ToggleShowcaseViewModel

sealed interface HomeMenuSectionItem : MenuSectionItem {
    data class ToggleShowcase(
        override val viewModel: ToggleShowcaseViewModel,
        override val title: String
    ) : HomeMenuSectionItem

    data class TextShowcase(
        override val viewModel: TextShowcaseViewModel,
        override val title: String
    ) : HomeMenuSectionItem

    data class ButtonShowcase(
        override val viewModel: ButtonShowcaseViewModel,
        override val title: String
    ) : HomeMenuSectionItem

    data class CarouselShowcase(
        override val viewModel: CarouselShowcaseViewModel,
        override val title: String
    ) : HomeMenuSectionItem

    data class TopNavigationShowcase(
        override val viewModel: CarouselShowcaseViewModel,
        override val title: String
    ) : HomeMenuSectionItem
}
