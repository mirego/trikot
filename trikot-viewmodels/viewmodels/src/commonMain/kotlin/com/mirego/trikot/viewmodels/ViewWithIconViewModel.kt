package com.mirego.trikot.viewmodels

import com.mirego.trikot.viewmodels.properties.Alignment
import com.mirego.trikot.viewmodels.properties.Color
import com.mirego.trikot.viewmodels.properties.StateSelector
import com.mirego.trikot.viewmodels.resource.ImageResource
import org.reactivestreams.Publisher

interface ViewWithIconViewModel : ViewModel {
    /**
     * Position of the image related to the text
     */
    val imageAlignment: Publisher<Alignment>
    /**
     * Ressource of the button image. Can be associated with a text
     */
    val imageResource: Publisher<StateSelector<ImageResource>>
    /**
     * ImageResource tint color
     */
    val tintColor: Publisher<StateSelector<Color>>
}
