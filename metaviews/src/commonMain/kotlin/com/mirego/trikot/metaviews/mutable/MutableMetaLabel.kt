package com.mirego.trikot.metaviews.mutable

import com.mirego.trikot.metaviews.MetaLabel
import com.mirego.trikot.metaviews.factory.PropertyFactory
import com.mirego.trikot.metaviews.properties.Color
import com.mirego.trikot.metaviews.properties.MetaSelector
import com.mirego.trikot.metaviews.text.RichText
import org.reactivestreams.Publisher

open class MutableMetaLabel : MutableMetaView(), MetaLabel {
    override var text = PropertyFactory.create("")

    override var richText: Publisher<RichText>? = null

    override var textColor = PropertyFactory.create(MetaSelector<Color>())
}
