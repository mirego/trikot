package com.mirego.trikot.viewmodels.mutable

import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.viewmodels.ToggleSwitchViewModel
import com.mirego.trikot.viewmodels.factory.PropertyFactory
import com.mirego.trikot.viewmodels.properties.ViewModelAction
import org.reactivestreams.Publisher

open class MutableToggleSwitchViewModel() : MutableViewModel(), ToggleSwitchViewModel {
    override var checked: Publisher<Boolean> = Publishers.behaviorSubject(false)
    override var enabled = PropertyFactory.never<Boolean>()
    override var toggleSwitchAction = PropertyFactory.create(ViewModelAction.None)
    override var action = Publishers.never<ViewModelAction>()
}
