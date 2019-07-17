package com.mirego.trikot.metaviews

import com.mirego.trikot.metaviews.properties.InputType
import org.reactivestreams.Publisher

interface MetaInputText : MetaLabel {

    /**
     * Text input by the user
     */
    val userInput: Publisher<String>

    /**
     * Type of data being placed in an EditText
     */
    val inputType: Publisher<InputType>

    /**
     * Text to display when the text is empty.
     */
    val placeholderText: Publisher<String>

    fun setUserInput(value: String)
}
