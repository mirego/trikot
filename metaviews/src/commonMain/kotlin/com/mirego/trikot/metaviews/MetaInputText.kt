package com.mirego.trikot.metaviews

import com.mirego.trikot.metaviews.properties.Color
import com.mirego.trikot.metaviews.properties.MetaInputType
import org.reactivestreams.Publisher

interface MetaInputText : MetaView {
    /**
     * Text input by the user
     */
    val userInput: Publisher<String>
    /**
     * Type of data being placed in an EditText
     */
    val inputType: Publisher<MetaInputType>
    /**
     * Text to display when the text is empty.
     */
    val placeholderText: Publisher<String>
    /**
     * Input text color
     */
    val textColor: Publisher<Color>
    /**
     * Set the text entered by the platform InputText.
     */
    fun setUserInput(value: String)
}
