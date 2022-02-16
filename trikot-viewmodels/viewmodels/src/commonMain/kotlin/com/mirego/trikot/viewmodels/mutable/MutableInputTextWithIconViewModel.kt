package com.mirego.trikot.viewmodels.mutable

import com.mirego.trikot.viewmodels.InputTextWithIconViewModel
import com.mirego.trikot.viewmodels.ViewWithIconViewModel
import com.mirego.trikot.viewmodels.factory.PropertyFactory
import com.mirego.trikot.viewmodels.properties.Alignment
import com.mirego.trikot.viewmodels.properties.Color
import com.mirego.trikot.viewmodels.properties.StateSelector
import com.mirego.trikot.viewmodels.resource.ImageResource

open class MutableInputTextWithIconViewModel : MutableInputTextViewModel(), InputTextWithIconViewModel {
    override var imageAlignment = PropertyFactory.create(Alignment.RIGHT)

    override var imageResource = PropertyFactory.create(StateSelector<ImageResource>())

    override var tintColor = PropertyFactory.create(StateSelector<Color>())
}
