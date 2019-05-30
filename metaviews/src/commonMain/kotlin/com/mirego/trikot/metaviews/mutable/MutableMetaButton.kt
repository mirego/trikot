package com.mirego.trikot.metaviews.mutable

import com.mirego.trikot.metaviews.MetaButton
import com.mirego.trikot.metaviews.factory.PropertyFactory
import com.mirego.trikot.metaviews.properties.Alignment
import com.mirego.trikot.metaviews.properties.Color
import com.mirego.trikot.metaviews.properties.MetaSelector
import com.mirego.trikot.metaviews.resource.ImageResource

open class MutableMetaButton : MutableMetaLabel(), MetaButton {
    override var backgroundImageResource = PropertyFactory.create(MetaSelector<ImageResource>())

    override var enabled = PropertyFactory.create(true)

    override var imageAlignment = PropertyFactory.create(Alignment.CENTER)

    override var imageResource = PropertyFactory.create(MetaSelector<ImageResource>())

    override var selected = PropertyFactory.create(false)

    override var tintColor = PropertyFactory.create(MetaSelector<Color>())
}
