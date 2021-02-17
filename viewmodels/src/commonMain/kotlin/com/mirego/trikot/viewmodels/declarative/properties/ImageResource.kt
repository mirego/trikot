package com.mirego.trikot.viewmodels.declarative.properties

import com.mirego.trikot.foundation.concurrent.freeze

/**
 * An image resource within a known set of images contained in the application. [ImageResource.None]
 * is used to represent the absence of image. An enum containing all the known images usually
 * conforms to this interface.
 */
interface ImageResource {
    companion object {
        val None = freeze(NoImageResource() as ImageResource)
    }
}

private class NoImageResource : ImageResource
