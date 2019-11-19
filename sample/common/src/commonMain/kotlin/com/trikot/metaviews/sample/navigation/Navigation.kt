package com.trikot.metaviews.sample.navigation

import com.trikot.metaviews.sample.viewmodels.home.*

interface NavigationDelegate {
    fun navigateTo(destination: Destination)
    fun showAlert(text: String)
}

enum class Destination(val getViewModel: (NavigationDelegate) -> ListViewModel) {
    VIEWS({ ViewsViewModel(it) } ),
    LABELS({ LabelsViewModel(it) } ),
    BUTTONS({ ButtonsViewModel(it) } ),
    IMAGES({ ImagesViewModel(it) } ),
    INPUT_TEXT({ InputTextViewModel(it) } )
}
