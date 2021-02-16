package com.mirego.trikot.viewmodels.declarative.resource

import com.mirego.trikot.foundation.concurrent.freeze

interface ImageResource {
    companion object {
        val None = freeze(NoImageResource() as ImageResource)
    }
}

class NoImageResource : ImageResource
