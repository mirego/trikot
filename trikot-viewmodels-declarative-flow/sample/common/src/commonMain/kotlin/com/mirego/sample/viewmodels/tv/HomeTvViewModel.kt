package com.mirego.sample.viewmodels.tv

import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel

interface HomeTvViewModel : VMDViewModel {
    val menuItems: List<HomeMenuSectionItem>
}
