package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.viewmodels.declarative.components.VMDPickerItemViewModel
import com.mirego.trikot.viewmodels.declarative.content.VMDContent
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import kotlinx.coroutines.CoroutineScope

class VMDContentPickerItemViewModelImpl<C : VMDContent>(
    coroutineScope: CoroutineScope,
    val content: C,
    override val identifier: String,
    override var isEnabled: Boolean = true,
    override var isHidden: Boolean = false
) : VMDViewModelImpl(coroutineScope), VMDPickerItemViewModel
