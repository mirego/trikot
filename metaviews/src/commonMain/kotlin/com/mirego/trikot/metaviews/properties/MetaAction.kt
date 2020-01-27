package com.mirego.trikot.metaviews.properties

import com.mirego.trikot.foundation.concurrent.freeze

typealias MetaActionBlock = (actionContext: Any?) -> Unit

open class MetaAction(private var metaAction: MetaActionBlock) {
    fun execute() {
        execute(null)
    }

    open fun execute(actionContext: Any? = null) {
        metaAction(actionContext)
    }

    companion object {
        val None = freeze(MetaAction {})
    }
}
