package com.mirego.sample.viewmodels.showcase.components.carousel

import com.mirego.sample.KWordTranslation
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.viewmodel.remoteImage
import com.mirego.trikot.viewmodels.declarative.viewmodel.text
import kotlinx.coroutines.CoroutineScope

class CarouselShowcaseViewModelImpl(i18N: I18N, coroutineScope: CoroutineScope) : ShowcaseViewModelImpl(coroutineScope), CarouselShowcaseViewModel {
    override val items: List<CarouselItemContent> = listOf(
        CarouselItemContent(
            title = "Item 1",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore" +
                " magna aliqua. Blandit aliquam etiam erat velit scelerisque in dictum non.",
            image = remoteImage("https://picsum.photos/1280/720")
        ),
        CarouselItemContent(
            title = "Item 2",
            description = "Volutpat commodo sed egestas egestas fringilla phasellus faucibus scelerisque eleifend.",
            image = remoteImage("https://picsum.photos/1280/721")
        ),
        CarouselItemContent(
            title = "Item 3",
            description = "Tellus in hac habitasse platea dictumst. Fermentum leo vel orci porta non pulvinar neque laoreet. Volutpat ac tincidunt vitae semper.",
            image = remoteImage("https://picsum.photos/1280/722")
        )
    )
    override val title = text(i18N[KWordTranslation.CAROUSEL_SHOWCASE_TITLE])
}
