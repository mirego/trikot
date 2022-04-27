package com.mirego.sample.viewmodels.showcase.components.list

import com.mirego.sample.viewmodels.showcase.ShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDListViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.content.VMDIdentifiableContentWrapper

interface ListShowcaseViewModel : ShowcaseViewModel {
    val listViewModel: VMDListViewModel<VMDIdentifiableContentWrapper<VMDTextViewModel>>
}
