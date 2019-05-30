package com.mirego.trikot.metaview.mutable

import com.mirego.trikot.metaview.MetaLabel
import com.mirego.trikot.metaview.factory.PropertyFactory
import com.mirego.trikot.metaview.properties.Color
import com.mirego.trikot.metaview.properties.MetaSelector
import com.mirego.trikot.metaview.resource.Font

open class MutableMetaLabel : MutableMetaView(), MetaLabel {
    override var text = PropertyFactory.create("")

    override var fontFace = PropertyFactory.create(Font.None)

    override var fontSize = PropertyFactory.create(15)

    override var textColor = PropertyFactory.create(MetaSelector<Color>())
}
