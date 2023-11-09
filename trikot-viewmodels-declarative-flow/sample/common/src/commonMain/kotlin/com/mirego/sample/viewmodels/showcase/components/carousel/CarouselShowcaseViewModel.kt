package com.mirego.sample.viewmodels.showcase.components.carousel

import com.mirego.sample.viewmodels.showcase.ShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDImageViewModel

interface CarouselShowcaseViewModel : ShowcaseViewModel {
    val items: List<CarouselItemContent>
}

data class CarouselItemContent(
    val title: String,
    val description: String,
    val image: VMDImageViewModel
)
