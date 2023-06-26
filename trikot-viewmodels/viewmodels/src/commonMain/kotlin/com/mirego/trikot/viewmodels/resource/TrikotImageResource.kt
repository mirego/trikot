package com.mirego.trikot.viewmodels.resource

interface TrikotImageResource {
    companion object {
        val None: TrikotImageResource = NoTrikotImageResource()
    }
}

class NoTrikotImageResource : TrikotImageResource
