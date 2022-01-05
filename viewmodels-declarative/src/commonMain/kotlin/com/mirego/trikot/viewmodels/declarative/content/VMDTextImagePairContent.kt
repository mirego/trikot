package com.mirego.trikot.viewmodels.declarative.content

import com.mirego.trikot.viewmodels.declarative.properties.VMDImageResource

/**
 * Utility class to represent a text and image pair as content of a component.
 * It is often used in buttons for example.
 */
data class VMDTextImagePairContent(
    val text: String,
    val image: VMDImageResource
) : VMDContent
