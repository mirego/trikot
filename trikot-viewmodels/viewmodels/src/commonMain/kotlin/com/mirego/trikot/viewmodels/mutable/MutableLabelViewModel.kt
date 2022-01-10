package com.mirego.trikot.viewmodels.mutable

import com.mirego.trikot.viewmodels.LabelViewModel
import com.mirego.trikot.viewmodels.factory.PropertyFactory
import com.mirego.trikot.viewmodels.properties.Color
import com.mirego.trikot.viewmodels.properties.StateSelector
import com.mirego.trikot.viewmodels.text.RichText
import org.reactivestreams.Publisher

open class MutableLabelViewModel : MutableViewModel(), LabelViewModel {
    override var text = PropertyFactory.create("")

    override var richText: Publisher<RichText>? = null

    override var textColor = PropertyFactory.create(StateSelector<Color>())
}
