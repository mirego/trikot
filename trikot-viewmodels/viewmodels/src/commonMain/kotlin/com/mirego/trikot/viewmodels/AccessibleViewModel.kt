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
    val accessibilityHint: Publisher<ViewModelAccessibilityHint>
}

/**
 * Accessibility hint
 *
 * @property hint                           Describes the result of performing an action on the element.
 * @property announceHintChanges            Indicates whether to announce the new hint when it changes. Default is true.
 * @property customHintsChangeAnnouncement  A custom hint that is announced only when the action changes and the view is already focussed.
 *                                          `hint` will be used if no value is specified for `customHintsChangeAnnouncement`.
 *                                          This is particularly useful for toggles. Ex : "Interact again to cancel your purchase"
 */
data class ViewModelAccessibilityHint(
    val hint: String,
    val announceHintChanges: Boolean = true,
    val customHintsChangeAnnouncement: String? = null
)
