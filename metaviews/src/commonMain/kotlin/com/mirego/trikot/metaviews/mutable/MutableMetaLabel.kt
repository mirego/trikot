package com.mirego.trikot.metaviews.mutable

import com.mirego.trikot.metaviews.MetaLabel
import com.mirego.trikot.metaviews.factory.PropertyFactory
import com.mirego.trikot.metaviews.properties.Color
import com.mirego.trikot.metaviews.properties.MetaSelector
import com.mirego.trikot.metaviews.resource.Font

open class MutableMetaLabel : MutableMetaView(), MetaLabel {
    override var text = PropertyFactory.create("")

    override var fontFace = PropertyFactory.create(Font.None)

    override var fontSize = PropertyFactory.create(15)

    override var textColor = PropertyFactory.create(MetaSelector<Color>())
}
