package com.mirego.trikot.viewmodels.declarative.properties

/**
 * An image resource within a known set of images contained in the application. [VMDImageResource.None]
 * is used to represent the absence of image. An enum containing all the known images usually
 * conforms to this interface.
 */
interface VMDImageResource {
    companion object {
        val None = VMDNoImageResource() as VMDImageResource
    }
}

class VMDNoImageResource : VMDImageResource
