package com.mirego.trikot.viewmodels.declarative.properties

/**
 * A value that represents either a local or a remote image, including the proper associated
 * values in each case.
 */
sealed class VMDImageDescriptor {
    data class Local(
        val imageResource: VMDImageResource
    ) : VMDImageDescriptor()

    data class Remote(
        val url: String,
        val placeholderImageResource: VMDImageResource = VMDImageResource.None
    ) : VMDImageDescriptor()
}
