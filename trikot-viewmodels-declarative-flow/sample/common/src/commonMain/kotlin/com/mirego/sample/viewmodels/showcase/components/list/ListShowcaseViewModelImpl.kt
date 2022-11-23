package com.mirego.sample.viewmodels.showcase.components.list

import com.mirego.sample.KWordTranslation
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.components.VMDListViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.content.VMDIdentifiableContentWrapper
import com.mirego.trikot.viewmodels.declarative.viewmodel.list
import com.mirego.trikot.viewmodels.declarative.viewmodel.text
import kotlinx.coroutines.CoroutineScope

class ListShowcaseViewModelImpl(i18N: I18N, coroutineScope: CoroutineScope) : ShowcaseViewModelImpl(coroutineScope), ListShowcaseViewModel {
    override val title: VMDTextViewModel = text(i18N[KWordTranslation.LIST_SHOWCASE_TITLE])
    override val listViewModel: VMDListViewModel<VMDIdentifiableContentWrapper<VMDTextViewModel>> = list(
        VMDIdentifiableContentWrapper("item_1", text("Item 1")),
        VMDIdentifiableContentWrapper("item_2", text("Item 2")),
        VMDIdentifiableContentWrapper("item_3", text("Item 3"))
    )
}
