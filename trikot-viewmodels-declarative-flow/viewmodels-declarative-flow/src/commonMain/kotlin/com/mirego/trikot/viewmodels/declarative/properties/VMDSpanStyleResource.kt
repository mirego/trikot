package com.mirego.trikot.viewmodels.declarative.properties

/**
 * A span style resource within a known set of styles contained in the application. [VMDSpanStyleResource.None]
 * is used to represent the absence of style. An enum containing all the known span styles usually
 * conforms to this interface.
 */
interface VMDSpanStyleResource {
    companion object {
        val None = VMDNoSpanStyleResource() as VMDSpanStyleResource
    }
}

class VMDNoSpanStyleResource : VMDSpanStyleResource
