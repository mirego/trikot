package com.mirego.trikot.viewmodels

import com.mirego.trikot.viewmodels.properties.Color
import com.mirego.trikot.viewmodels.properties.StateSelector
import com.mirego.trikot.viewmodels.properties.ViewModelAction
import org.reactivestreams.Publisher

interface ViewModel : AccessibleViewModel {
    /**
     * Alpha value of the view. 0.0 to 1.0
     */
    val alpha: Publisher<Float>

    /**
     * Background color of the view
     */
    val backgroundColor: Publisher<StateSelector<Color>>

    /**
     * Is view hidden or not. Should not take any place in the UI.
     * Should respect View.GONE Android behavior
     */
    val hidden: Publisher<Boolean>

    /**
     * Action to execute when the view is tapped
     */
    val action: Publisher<ViewModelAction>
}
