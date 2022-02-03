package com.mirego.trikot.viewmodels

import org.reactivestreams.Publisher

interface AccessibleViewModel {
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
