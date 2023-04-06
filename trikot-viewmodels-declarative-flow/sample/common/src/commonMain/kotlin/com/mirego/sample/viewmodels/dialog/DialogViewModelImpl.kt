package com.mirego.sample.viewmodels.dialog

import com.mirego.sample.navigation.DialogInput
import com.mirego.sample.navigation.DialogResult
import com.mirego.trikot.viewmodels.declarative.navigation.VMDNavigationController
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.buttonWithText
import com.mirego.trikot.viewmodels.declarative.viewmodel.text
import kotlinx.coroutines.CoroutineScope

class DialogViewModelImpl(
    input: DialogInput,
    navigationController: VMDNavigationController,
    coroutineScope: CoroutineScope
) : VMDViewModelImpl(coroutineScope), DialogViewModel {

    override val message = text(input.message)
    override val leftButton = buttonWithText(text = input.choice1, action = {
        navigationController.pop(DialogResult(choice = input.choice1))
    })
    override val rightButton = buttonWithText(text = input.choice2, action = {
        navigationController.pop(DialogResult(choice = input.choice2))
    })
}
