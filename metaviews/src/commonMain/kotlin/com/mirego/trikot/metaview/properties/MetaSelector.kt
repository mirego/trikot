package com.mirego.trikot.metaview.properties

data class MetaSelector<T>(
    val default: T? = null,
    val highlighted: T? = null,
    val selected: T? = null,
    val disabled: T? = null
)
