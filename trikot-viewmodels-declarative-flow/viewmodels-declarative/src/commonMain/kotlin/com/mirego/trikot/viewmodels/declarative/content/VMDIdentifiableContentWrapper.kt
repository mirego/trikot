package com.mirego.trikot.viewmodels.declarative.content

data class VMDIdentifiableContentWrapper<C : VMDContent>(
    override val identifier: String,
    override val content: C
) : VMDIdentifiableContentAbstract<C>()
