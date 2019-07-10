package com.mirego.trikot.metaviews

import com.mirego.trikot.metaviews.properties.InputType
import org.reactivestreams.Publisher

interface MetaInputText : MetaLabel {

    /**
     * Type of data being placed in an EditText
     */
    val inputType: Publisher<InputType>

    /**
     * Hint text to display when the text is empty.
     */
    val hint: Publisher<String>?
}
