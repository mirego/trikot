package com.mirego.sample.viewmodels.home

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.VMDButtonViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDListViewModel
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.content.VMDIdentifiableContentAbstract
import com.mirego.trikot.viewmodels.declarative.content.VMDIdentifiableContentWrapper
import com.mirego.trikot.viewmodels.declarative.content.VMDTextContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl

class HomeViewModelPreview :
    VMDViewModelImpl(cancellableManager = CancellableManager()),
    HomeViewModel {

    override val title =
        VMDComponents.Text.withContent("Components", cancellableManager)

    override val items: VMDListViewModel<VMDIdentifiableContentAbstract<VMDButtonViewModel<VMDTextContent>>> =
        VMDComponents.List.of(
            VMDIdentifiableContentWrapper(
                "1",
                VMDComponents.Button.withText(
                    "Component 1",
                    cancellableManager
                )
            ),
            VMDIdentifiableContentWrapper(
                "2",
                VMDComponents.Button.withText(
                    "Component 2",
                    cancellableManager
                )
            ),
            VMDIdentifiableContentWrapper(
                "3",
                VMDComponents.Button.withText(
                    "Component 3",
                    cancellableManager
                )
            ),
            cancellableManager = cancellableManager
        )
}
