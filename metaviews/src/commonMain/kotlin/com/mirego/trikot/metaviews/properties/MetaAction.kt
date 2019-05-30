package com.mirego.trikot.metaviews.properties

import com.mirego.trikot.streams.concurrent.freeze

typealias MetaActionBlock = (actionContext: Any?) -> Unit

class MetaAction(private var metaAction: MetaActionBlock) {
    fun execute() {
        execute(null)
    }

    fun execute(actionContext: Any? = null) {
        metaAction(actionContext)
    }

    companion object {
        val None = freeze(MetaAction {})
    }
}
