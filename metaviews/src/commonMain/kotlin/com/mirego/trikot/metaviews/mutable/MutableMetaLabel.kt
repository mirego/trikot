package com.mirego.trikot.metaviews.mutable

import com.mirego.trikot.metaviews.MetaLabel
import com.mirego.trikot.metaviews.factory.PropertyFactory
import com.mirego.trikot.metaviews.properties.Color
import com.mirego.trikot.metaviews.properties.MetaSelector
import com.mirego.trikot.metaviews.resource.Font
import com.mirego.trikot.metaviews.text.RichText
import org.reactivestreams.Publisher

open class MutableMetaLabel : MutableMetaView(), MetaLabel {
    override var text = PropertyFactory.create("")

    override val richTexts: Publisher<RichText>? = null

    override var fontFace = PropertyFactory.create(Font.None)

    override var fontSize = PropertyFactory.create(15)

    override var textColor = PropertyFactory.create(MetaSelector<Color>())
}
