package com.mirego.trikot.viewmodels.declarative.navigation

data class VMDPushOptions(
    val presentationMode: VMDPresentationMode
)

enum class VMDPresentationMode {
    PUSHED,
    MODAL,
    DIALOG,
    BOTTOM_SHEET,
    UNDEFINED
}
