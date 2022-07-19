package com.mirego.trikot.viewmodels.declarative.content

import com.mirego.trikot.viewmodels.declarative.properties.VMDImageResource
import kotlinx.coroutines.flow.Flow

/**
 * Utility class to represent a text and image pair as content of a component using flows.
 * It is useful in buttons when using Flowi18N for example.
 */
data class VMDTextFlowImagePairContent(
    val text: Flow<String>,
    val image: VMDImageResource
) : VMDContent
