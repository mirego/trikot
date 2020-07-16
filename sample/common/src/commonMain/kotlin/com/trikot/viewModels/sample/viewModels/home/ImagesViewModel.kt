package com.trikot.viewmodels.sample.viewmodels.home

import com.mirego.trikot.viewmodels.ImageFlow
import com.mirego.trikot.viewmodels.mutable.simpleImageFlowProvider
import com.mirego.trikot.viewmodels.properties.Color
import com.mirego.trikot.viewmodels.properties.ImageState
import com.mirego.trikot.viewmodels.properties.ViewModelAction
import com.mirego.trikot.viewmodels.properties.StateSelector
import com.mirego.trikot.viewmodels.properties.SimpleImageFlow
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.streams.reactive.map
import com.mirego.trikot.viewmodels.ListItemViewModel
import com.mirego.trikot.viewmodels.mutable.MutableListViewModel
import com.trikot.viewmodels.sample.viewmodels.MutableHeaderListItemViewModel
import com.trikot.viewmodels.sample.viewmodels.MutableImageListItemViewModel
import com.trikot.viewmodels.sample.viewmodels.MutableViewListItemViewModel
import com.trikot.viewmodels.sample.navigation.NavigationDelegate
import com.trikot.viewmodels.sample.resource.ImageResources
import org.reactivestreams.Publisher

class ImagesViewModel(navigationDelegate: NavigationDelegate) : MutableListViewModel<ListItemViewModel>() {
    private val fallbackImageFlow =
        Publishers.behaviorSubject(SimpleImageFlow(url = "https://www.vokode.com/wp-content/uploads/2019/06/fallback.jpg") as ImageFlow)

    override var elements: Publisher<List<ListItemViewModel>> = listOf<ListItemViewModel>(
        MutableHeaderListItemViewModel(".backgroundColor"),
        MutableViewListItemViewModel().also {
            it.view.backgroundColor = StateSelector(Color(143, 143, 143)).just()
        },
        MutableHeaderListItemViewModel(".alpha"),
        MutableImageListItemViewModel(simpleImageFlowProvider()).also {
            it.image.alpha = 0.5f.just()
        },
        MutableHeaderListItemViewModel(".hidden"),
        MutableImageListItemViewModel(simpleImageFlowProvider()).also {
            it.image.hidden = true.just()
        },
        MutableHeaderListItemViewModel(".onTap"),
        MutableImageListItemViewModel(simpleImageFlowProvider()).also {
            it.image.action = ViewModelAction { navigationDelegate.showAlert("Tapped $it") }.just()
        },
        MutableHeaderListItemViewModel(".imageResource"),
        MutableImageListItemViewModel(simpleImageFlowProvider(imageResource = ImageResources.ICON)),
        MutableHeaderListItemViewModel(".imageResource + tintColor"),
        MutableImageListItemViewModel(
            simpleImageFlowProvider(
                imageResource = ImageResources.ICON,
                tintColor = Color(255, 0, 0)
            )
        ),
        MutableHeaderListItemViewModel(".placeholder"),
        MutableImageListItemViewModel(simpleImageFlowProvider(placeholderImageResource = ImageResources.ICON)),
        MutableHeaderListItemViewModel(".placeholder + .url"),
        MutableImageListItemViewModel(
            simpleImageFlowProvider(
                url = "https://images5.alphacoders.com/346/thumb-1920-346532.jpg",
                placeholderImageResource = ImageResources.ICON
            )
        ),
        MutableHeaderListItemViewModel("url + .onSuccess"),
        MutableImageListItemViewModel({ _, _ ->
            Publishers.behaviorSubject(
                SimpleImageFlow(
                    url = "https://images5.alphacoders.com/346/thumb-1920-346532.jpg",
                    onSuccess = fallbackImageFlow
                )
            )
        }),
        MutableHeaderListItemViewModel("url + .onError"),
        MutableImageListItemViewModel({ _, _ ->
            Publishers.behaviorSubject(
                SimpleImageFlow(
                    url = "https://not.existing.url.foo",
                    onError = fallbackImageFlow
                )
            )
        }),
        MutableHeaderListItemViewModel("url + errorState"),
        MutableImageListItemViewModel({ _, _ -> Publishers.behaviorSubject(SimpleImageFlow(url = "https://not.existing.url.foo")) }).apply {
            image.backgroundColor = image.imageState.map {
                StateSelector(
                    if (it == ImageState.ERROR) Color(
                        255,
                        0,
                        0
                    ) else Color(0, 255, 0)
                )
            }
        }
    ).just()
}
