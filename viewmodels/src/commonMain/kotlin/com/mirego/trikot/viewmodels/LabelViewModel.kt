package com.mirego.trikot.viewmodels

import com.mirego.trikot.viewmodels.properties.Color
import com.mirego.trikot.viewmodels.properties.StateSelector
import com.mirego.trikot.viewmodels.text.RichText
import org.reactivestreams.Publisher

interface LabelViewModel : ViewModel {
    /**
     * Label text
     */
    val text: Publisher<String>
    /**
      * Label rich text. Use this instead of {@link #text text} when not null
     */
    val richText: Publisher<RichText>?
    /**
     * Label text color
     */
    val textColor: Publisher<StateSelector<Color>>
}
