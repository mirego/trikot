package com.mirego.trikot.viewmodels.declarative.properties

import com.mirego.trikot.viewmodels.declarative.components.ImageViewModel
import com.mirego.trikot.viewmodels.declarative.components.TextViewModel

/**
 * Utility class to represent a text and image pair as content of a component.
 * It is often used in buttons for example.
 */
data class TextImagePair(
    val text: TextViewModel,
    val image: ImageViewModel
) : Content
