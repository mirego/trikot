package com.mirego.trikot.viewmodels.declarative.content

/**
 * Represent content that can be identified. Usually used within a list to compute
 * diff between updates.
 */
interface VMDIdentifiableContent : VMDContent {
    val identifier: String
}
