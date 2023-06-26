package com.mirego.trikot.viewmodels

import com.mirego.trikot.viewmodels.properties.Alignment
import com.mirego.trikot.viewmodels.properties.Color
import com.mirego.trikot.viewmodels.properties.StateSelector
import com.mirego.trikot.viewmodels.resource.TrikotImageResource
import org.reactivestreams.Publisher

interface ButtonViewModel : LabelViewModel {
    /**
     * Resource for the background image of the button
     */
    val backgroundImageResource: Publisher<StateSelector<TrikotImageResource>>

    /**
     * If the button is enabled or disabled
     */
    val enabled: Publisher<Boolean>

    /**
     * Position of the image related to the text
     */
    val imageAlignment: Publisher<Alignment>

    /**
     * Ressource of the button image. Can be associated with a text
     */
    val imageResource: Publisher<StateSelector<TrikotImageResource>>

    /**
     * Selected state of the view
     */
    val selected: Publisher<Boolean>

    /**
     * ImageResource tint color
     */
    val tintColor: Publisher<StateSelector<Color>>
}
