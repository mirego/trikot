package com.mirego.trikot.viewmodels.declarative.properties

/**
 * A value that represents either a local or a remote image, including the proper associated
 * values in each case.
 */
sealed class ImageDescriptor {
    data class Local(
        val imageResource: ImageResource
    ) : ImageDescriptor()

    data class Remote(
        val url: String,
        val placeholderImageResource: ImageResource = ImageResource.None
    ) : ImageDescriptor()
}
