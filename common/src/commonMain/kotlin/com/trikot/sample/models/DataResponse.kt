package com.trikot.sample.models

import kotlinx.serialization.Serializable

@Serializable
data class DataResponse<T>(val data: T)
