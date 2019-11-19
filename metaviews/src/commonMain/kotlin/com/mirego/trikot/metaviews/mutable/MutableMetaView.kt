package com.mirego.trikot.metaviews.mutable

import com.mirego.trikot.metaviews.MetaView
import com.mirego.trikot.metaviews.factory.PropertyFactory
import com.mirego.trikot.metaviews.properties.Color
import com.mirego.trikot.metaviews.properties.MetaSelector
import com.mirego.trikot.metaviews.properties.MetaAction

open class MutableMetaView : MetaView {
    override var alpha = PropertyFactory.create(1f)

    override var backgroundColor = PropertyFactory.create(MetaSelector<Color>())

    override var hidden = PropertyFactory.create(false)

    override var onTap = PropertyFactory.create(MetaAction.None)
}
