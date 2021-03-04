package com.mirego.trikot.viewmodels.declarative.properties

/**
 * Utility class to represent a text and image pair as content of a component.
 * It is often used in buttons for example.
 */
data class TextImagePairContent(
    val text: String,
    val image: ImageResource
) : Content
