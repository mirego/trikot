package com.mirego.trikot.viewmodels.declarative.content

import com.mirego.trikot.viewmodels.declarative.properties.VMDImageResource

data class VMDImageContent(
    val image: VMDImageResource,
    val contentDescription: String? = null,
) : VMDContent
