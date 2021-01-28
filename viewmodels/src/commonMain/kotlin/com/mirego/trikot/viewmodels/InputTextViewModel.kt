package com.mirego.trikot.viewmodels

import com.mirego.trikot.viewmodels.properties.Color
import com.mirego.trikot.viewmodels.properties.InputTextEditorAction
import com.mirego.trikot.viewmodels.properties.InputTextType
import org.reactivestreams.Publisher

interface InputTextViewModel : ViewModel {
    /**
     * Text input by the user
     */
    val userInput: Publisher<String>
    /**
     * Type of data being placed in an EditText
     */
    val inputType: Publisher<InputTextType>
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
    /**
     * Action to execute when keyboard action key is pressed
     * Return true if you have consumed the action, else false.
     */
    val editorAction: Publisher<InputTextEditorAction>
}
