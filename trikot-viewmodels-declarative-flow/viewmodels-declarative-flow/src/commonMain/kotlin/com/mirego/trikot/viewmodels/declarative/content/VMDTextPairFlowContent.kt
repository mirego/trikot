package com.mirego.trikot.viewmodels.declarative.content

import kotlinx.coroutines.flow.Flow

/**
 * Utility class to represent a text pair as content of a component using flows.
 * It is useful in buttons when using Flowi18N for example.
 */
data class VMDTextPairFlowContent(
    val first: Flow<String>,
    val second: Flow<String>
) : VMDContent
