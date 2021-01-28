package com.mirego.trikot.viewmodels.properties

import com.mirego.trikot.foundation.concurrent.freeze

typealias ViewModelActionBlock = (actionContext: Any?) -> Unit

open class ViewModelAction(private var action: ViewModelActionBlock) {
    fun execute() {
        execute(null)
    }

    open fun execute(actionContext: Any? = null) {
        action(actionContext)
    }

    companion object {
        val None = freeze(ViewModelAction {})
    }
}
