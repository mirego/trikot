package com.mirego.trikot.viewmodels.declarative.components

data class VMDSnackbarViewData(
    val message: String,
    val duration: VMDSnackbarDuration = VMDSnackbarDuration.SHORT,
    val withDismissAction: Boolean = true
)

enum class VMDSnackbarDuration {
    SHORT,
    LONG,
    INDEFINITE
}
