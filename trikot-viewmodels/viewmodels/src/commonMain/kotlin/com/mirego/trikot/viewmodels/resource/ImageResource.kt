package com.mirego.trikot.viewmodels.resource

interface ImageResource {
    companion object {
        val None = NoImageResource() as ImageResource
    }
}

class NoImageResource : ImageResource
