package com.mirego.sample.viewmodels.showcase.components.list

import com.mirego.sample.KWordTranslation
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.components.VMDListViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.content.VMDIdentifiableContentWrapper
import kotlinx.coroutines.CoroutineScope

class ListShowcaseViewModelImpl(i18N: I18N, coroutineScope: CoroutineScope) : ShowcaseViewModelImpl(coroutineScope), ListShowcaseViewModel {
    override val title: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.LIST_SHOWCASE_TITLE], coroutineScope)
    override val listViewModel: VMDListViewModel<VMDIdentifiableContentWrapper<VMDTextViewModel>> = VMDComponents.List.of(
        VMDIdentifiableContentWrapper("item_1", VMDComponents.Text.withContent("Item 1", coroutineScope)),
        VMDIdentifiableContentWrapper("item_2", VMDComponents.Text.withContent("Item 2", coroutineScope)),
        VMDIdentifiableContentWrapper("item_3", VMDComponents.Text.withContent("Item 3", coroutineScope)),
        coroutineScope = coroutineScope
    )
}
