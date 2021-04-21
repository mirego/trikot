package com.mirego.trikot.viewmodels

import com.mirego.trikot.viewmodels.properties.ViewModelAction
import org.reactivestreams.Publisher

interface ToggleSwitchViewModel : ViewModel {
    /**
     * Currently selected state of the view
     */
    val isOn: Publisher<Boolean>

    /**
     * If the ToggleSwitch is enabled or disabled
     */
    val isEnabled: Publisher<Boolean>

    /**
     * Specific action that activates on value changed instead of on click to handle swiping
     */
    val toggleSwitchAction: Publisher<ViewModelAction>
}
