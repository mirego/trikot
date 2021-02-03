package com.mirego.trikot.viewmodels

import org.reactivestreams.Publisher

interface ToggleSwitchViewModel : ViewModel {
    /**
     * Currently selected state of the view
     */
    val isOn: Publisher<Boolean>

    /**
     * Set the state defined by the platform switch
     */
    fun setIsOn(on: Boolean)

    /**
     * If the ToggleSwitch is enabled or disabled
     */
    val enabled: Publisher<Boolean>
}
