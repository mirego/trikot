package com.mirego.sample.viewmodels.showcase.components.list

import com.mirego.sample.KWordTranslation
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.VMDListViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.content.VMDIdentifiableContentWrapper

class ListShowcaseViewModelImpl(i18N: I18N, cancellableManager: CancellableManager) : ShowcaseViewModelImpl(cancellableManager), ListShowcaseViewModel {
    override val title: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.LIST_SHOWCASE_TITLE], cancellableManager)
    override val listViewModel: VMDListViewModel<VMDIdentifiableContentWrapper<VMDTextViewModel>> = VMDComponents.List.of(
        VMDIdentifiableContentWrapper("item_1", VMDComponents.Text.withContent("Item 1", cancellableManager)),
        VMDIdentifiableContentWrapper("item_2", VMDComponents.Text.withContent("Item 2", cancellableManager)),
        VMDIdentifiableContentWrapper("item_3", VMDComponents.Text.withContent("Item 3", cancellableManager)),
        cancellableManager = cancellableManager
    )
}
