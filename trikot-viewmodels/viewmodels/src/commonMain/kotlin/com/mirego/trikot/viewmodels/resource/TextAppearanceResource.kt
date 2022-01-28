package com.mirego.trikot.viewmodels.resource

interface TextAppearanceResource {
    companion object {
        val None = NoTextAppearanceResource() as TextAppearanceResource
    }
}

class NoTextAppearanceResource : TextAppearanceResource
