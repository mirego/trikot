package com.mirego.trikot.metaviews.resource

import com.mirego.trikot.streams.concurrent.freeze

interface Font {
    companion object {
        val None = freeze(NoFont() as Font)
    }
}

class NoFont : Font
