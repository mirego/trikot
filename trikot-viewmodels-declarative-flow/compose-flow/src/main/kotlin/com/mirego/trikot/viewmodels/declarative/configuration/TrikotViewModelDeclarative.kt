package com.mirego.trikot.viewmodels.declarative.configuration

object TrikotViewModelDeclarative {
    private val uninitializedException: IllegalStateException
        get() {
            return IllegalStateException("TrikotViewModelDeclarative must be initialized before use")
        }

    private var internalImageProvider: VMDImageProvider? = null
    val imageProvider: VMDImageProvider
        get() {
            return internalImageProvider ?: throw uninitializedException
        }

    private var internalTextStyleProvider: VMDTextStyleProvider? = null
    val textStyleProvider: VMDTextStyleProvider
        get() {
            return internalTextStyleProvider ?: throw uninitializedException
        }

    fun initialize(imageProvider: VMDImageProvider = DefaultImageProvider(), textStyleProvider: VMDTextStyleProvider = DefaultTextStyleProvider()) {
        internalImageProvider = imageProvider
        internalTextStyleProvider = textStyleProvider
    }
}
