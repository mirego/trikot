package com.mirego.trikot.viewmodels.declarative.content

/**
 * Utility class to represent a text pair as content of a component.
 * It is often used in buttons for example.
 */
data class TextPairContent(
    val first: String,
    val second: String
) : Content
