package com.mirego.trikot.viewmodels.declarative.properties

/**
 * Represent the current progress of a determinate progress indicator.
 */
data class VMDProgressDetermination(val progress: Float = 0f, val total: Float = 1f) {
    val progressRatio: Float
        get() = if (total == 0f) 0f else progress / total
}
