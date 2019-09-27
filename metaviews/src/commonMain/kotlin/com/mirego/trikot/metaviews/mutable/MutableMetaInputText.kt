package com.mirego.trikot.metaviews.mutable

import com.mirego.trikot.metaviews.MetaInputText
import com.mirego.trikot.metaviews.factory.PropertyFactory
import com.mirego.trikot.metaviews.properties.MetaInputType
import com.mirego.trikot.streams.reactive.Publishers

open class MutableMetaInputText : MutableMetaLabel(), MetaInputText {
    override var userInput = Publishers.behaviorSubject("")

    override var inputType = PropertyFactory.create(MetaInputType.TEXT)

    override var placeholderText = PropertyFactory.create("")

    override fun setUserInput(value: String) {
        userInput.value = value
    }
}
