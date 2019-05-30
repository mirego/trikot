package com.mirego.trikot.metaview.mutable

import com.mirego.trikot.metaview.MetaButton
import com.mirego.trikot.metaview.factory.PropertyFactory
import com.mirego.trikot.metaview.properties.Alignment
import com.mirego.trikot.metaview.properties.Color
import com.mirego.trikot.metaview.properties.MetaSelector
import com.mirego.trikot.metaview.resource.ImageResource

open class MutableMetaButton : MutableMetaLabel(), MetaButton {
    override var backgroundImageResource = PropertyFactory.create(MetaSelector<ImageResource>())

    override var enabled = PropertyFactory.create(true)

    override var imageAlignment = PropertyFactory.create(Alignment.CENTER)

    override var imageResource = PropertyFactory.create(MetaSelector<ImageResource>())

    override var selected = PropertyFactory.create(false)

    override var tintColor = PropertyFactory.create(MetaSelector<Color>())
}
