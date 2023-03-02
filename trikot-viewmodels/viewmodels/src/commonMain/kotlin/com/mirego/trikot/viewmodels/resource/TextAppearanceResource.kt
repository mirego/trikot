package com.mirego.trikot.viewmodels.resource

interface TextAppearanceResource {
    companion object {
        val None: TextAppearanceResource = NoTextAppearanceResource()
    }
}

class NoTextAppearanceResource : TextAppearanceResource
