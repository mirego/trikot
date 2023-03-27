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

    var textStyleProvider: VMDTextStyleProvider? = null
        private set

    fun initialize(imageProvider: VMDImageProvider, textStyleProvider: VMDTextStyleProvider? = null) {
        internalImageProvider = imageProvider
        this.textStyleProvider = textStyleProvider
    }
}
