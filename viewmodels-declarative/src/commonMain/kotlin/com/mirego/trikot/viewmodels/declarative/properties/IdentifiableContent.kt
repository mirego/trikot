package com.mirego.trikot.viewmodels.declarative.properties

/**
 * Represent content that can be identified. Usually used within a list to compute
 * diff between updates.
 */
interface IdentifiableContent : Content {
    val identifier: String
}
