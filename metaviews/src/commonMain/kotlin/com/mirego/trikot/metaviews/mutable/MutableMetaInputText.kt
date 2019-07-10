package com.mirego.trikot.metaviews.mutable

import com.mirego.trikot.metaviews.MetaInputText
import com.mirego.trikot.metaviews.factory.PropertyFactory
import com.mirego.trikot.metaviews.properties.InputType
import com.mirego.trikot.streams.reactive.MutablePublisher

open class MutableMetaInputText : MutableMetaLabel(), MetaInputText {
    override var inputType: MutablePublisher<InputType> = PropertyFactory.create(InputType.TEXT)

    override var hint: MutablePublisher<String> = PropertyFactory.create("")
}
