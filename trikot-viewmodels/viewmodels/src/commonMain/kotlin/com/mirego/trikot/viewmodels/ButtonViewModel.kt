package com.mirego.trikot.viewmodels

import com.mirego.trikot.viewmodels.properties.StateSelector
import com.mirego.trikot.viewmodels.resource.ImageResource
import org.reactivestreams.Publisher

interface ButtonViewModel : LabelViewModel, ViewWithIconViewModel {
    /**
     * Resource for the background image of the button
     */
    val backgroundImageResource: Publisher<StateSelector<ImageResource>>
    /**
     * If the button is enabled or disabled
     */
    val enabled: Publisher<Boolean>
    /**
     * Selected state of the view
     */
    val selected: Publisher<Boolean>
}
