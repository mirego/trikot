package com.mirego.trikot.viewmodels.mutable

import com.mirego.trikot.viewmodels.ButtonViewModel
import com.mirego.trikot.viewmodels.factory.PropertyFactory
import com.mirego.trikot.viewmodels.properties.Alignment
import com.mirego.trikot.viewmodels.properties.Color
import com.mirego.trikot.viewmodels.properties.StateSelector
import com.mirego.trikot.viewmodels.resource.TrikotImageResource

open class MutableButtonViewModel : MutableLabelViewModel(), ButtonViewModel {
    override var backgroundImageResource = PropertyFactory.create(StateSelector<TrikotImageResource>())

    override var enabled = PropertyFactory.create(true)

    override var imageAlignment = PropertyFactory.create(Alignment.CENTER)

    override var imageResource = PropertyFactory.create(StateSelector<TrikotImageResource>())

    override var selected = PropertyFactory.create(false)

    override var tintColor = PropertyFactory.create(StateSelector<Color>())
}
