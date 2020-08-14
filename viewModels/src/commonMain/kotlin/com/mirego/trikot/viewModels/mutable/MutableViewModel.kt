package com.mirego.trikot.viewmodels.mutable

import com.mirego.trikot.viewmodels.ViewModel
import com.mirego.trikot.viewmodels.factory.PropertyFactory
import com.mirego.trikot.viewmodels.properties.Color
import com.mirego.trikot.viewmodels.properties.StateSelector
import com.mirego.trikot.viewmodels.properties.ViewModelAction

open class MutableViewModel : ViewModel {
    override var alpha = PropertyFactory.never<Float>()

    override var backgroundColor = PropertyFactory.create(StateSelector<Color>())

    override var hidden = PropertyFactory.never<Boolean>()

    override var action = PropertyFactory.create(ViewModelAction.None)
}
