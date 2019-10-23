package com.trikot.sample.models

import kotlinx.serialization.Serializable

@Serializable
data class Quote(val quote: String, val author: String)
