package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.VMDPickerItemViewModel
import com.mirego.trikot.viewmodels.declarative.content.VMDContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl

class VMDContentPickerItemViewModelImpl<C : VMDContent>(
    cancellableManager: CancellableManager,
    val content: C,
    override val identifier: String,
    override var isEnabled: Boolean = true,
    override var isHidden: Boolean = false
) : VMDViewModelImpl(cancellableManager), VMDPickerItemViewModel
