package com.mirego.trikot.viewmodels.declarative.content

abstract class VMDIdentifiableContentAbstract<C : VMDContent> : VMDIdentifiableContent {
    abstract override val identifier: String
    abstract val content: C
}
