package com.mirego.trikot.viewmodels.mutable

import com.mirego.trikot.viewmodels.InputTextViewModel
import com.mirego.trikot.viewmodels.factory.PropertyFactory
import com.mirego.trikot.viewmodels.properties.Color
import com.mirego.trikot.viewmodels.properties.InputTextType
import com.mirego.trikot.streams.reactive.Publishers

open class MutableInputTextViewModel : MutableViewModel(), InputTextViewModel {
    override var userInput = Publishers.behaviorSubject("")

    override var inputType = PropertyFactory.create(InputTextType.TEXT)

    override var textColor = PropertyFactory.create(Color.None)

    override var placeholderText = PropertyFactory.create("")

    override fun setUserInput(value: String) {
        userInput.value = value
    }
}
