package com.mirego.trikot.viewmodels

import com.mirego.trikot.viewmodels.properties.Color
import com.mirego.trikot.viewmodels.properties.StateSelector
import com.mirego.trikot.viewmodels.properties.ViewModelAction
import org.reactivestreams.Publisher

interface ViewModel {
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
    /**
     * Return true if it should be exposed as an accessibility element.
     * Note : `accessibilityLabel` or `accessibilityHint` must be specified (at least one) if this property is set to true or it will have no impact.
     */
    val isAccessibilityElement: Publisher<Boolean>
    /**
     * String that identifies the accessibility element
     */
    val accessibilityLabel: Publisher<String>
    /**
     * Describes the result of performing an action on the element, when the result is non-obvious.
     * For example: "Purchases the item." or "Downloads the attachment."
     */
    val accessibilityHint: Publisher<String>
}
