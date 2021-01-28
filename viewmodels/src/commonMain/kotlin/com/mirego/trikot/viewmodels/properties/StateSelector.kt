package com.mirego.trikot.viewmodels.properties

data class StateSelector<T>(
    val default: T? = null,
    val highlighted: T? = null,
    val selected: T? = null,
    val disabled: T? = null
) {
    val isEmpty: Boolean get() = default == null && highlighted == null && selected == null && disabled == null
}
