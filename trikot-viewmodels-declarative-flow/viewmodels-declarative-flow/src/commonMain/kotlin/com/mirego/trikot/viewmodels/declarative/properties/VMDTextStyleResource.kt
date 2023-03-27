package com.mirego.trikot.viewmodels.declarative.properties

/**
 * A text style resource within a known set of styles contained in the application. [VMDTextStyleResource.None]
 * is used to represent the absence of style. An enum containing all the known text styles usually
 * conforms to this interface.
 */
interface VMDTextStyleResource {
    companion object {
        val None = VMDNoTextStyleResource() as VMDTextStyleResource
    }
}

class VMDNoTextStyleResource : VMDTextStyleResource
