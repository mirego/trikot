package com.mirego.trikot.viewmodels.declarative.properties

import com.mirego.trikot.viewmodels.declarative.components.TextViewModel

/**
 * Utility class to represent a text pair as content of a component.
 * It is often used in buttons for example.
 */
data class TextPair(
    val first: TextViewModel,
    val second: TextViewModel
) : Content
