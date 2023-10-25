package com.mirego.sample.viewmodels.tv

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
}
