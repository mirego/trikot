package com.mirego.trikot.viewmodels.mutable

import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.viewmodels.ToggleSwitchViewModel

open class MutableToggleSwitchViewModel() : MutableViewModel(), ToggleSwitchViewModel {

    override val isOn = Publishers.behaviorSubject(false)

    override fun setIsOn(on: Boolean) {
        isOn.value = on
    }
}
