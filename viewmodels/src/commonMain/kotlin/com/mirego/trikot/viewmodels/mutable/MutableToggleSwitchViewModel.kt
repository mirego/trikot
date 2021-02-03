package com.mirego.trikot.viewmodels.mutable

import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.viewmodels.ToggleSwitchViewModel
import com.mirego.trikot.viewmodels.factory.PropertyFactory

open class MutableToggleSwitchViewModel() : MutableViewModel(), ToggleSwitchViewModel {

    override val isOn = Publishers.behaviorSubject(false)

    override fun setIsOn(on: Boolean) {
        isOn.value = on
    }

    override var enabled = PropertyFactory.never<Boolean>()
}
