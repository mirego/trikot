package com.mirego.trikot.metaview.mutable

import com.mirego.trikot.metaview.MetaView
import com.mirego.trikot.metaview.factory.PropertyFactory
import com.mirego.trikot.metaview.properties.Color
import com.mirego.trikot.metaview.properties.MetaSelector
import com.mirego.trikot.metaview.properties.MetaAction

abstract class MutableMetaView : MetaView {
    override var alpha = PropertyFactory.create(1f)

    override var backgroundColor = PropertyFactory.create(MetaSelector<Color>())

    override var hidden = PropertyFactory.create(false)

    override var isHighlightedBorderless = PropertyFactory.create(Color.None)

    override var onTap = PropertyFactory.create(MetaAction.None)
}
