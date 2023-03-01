package com.mirego.trikot.viewmodels.resource

interface ImageResource {
    companion object {
        val None: ImageResource = NoImageResource()
    }
}

class NoImageResource : ImageResource
