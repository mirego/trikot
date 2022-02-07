package com.mirego.trikot.viewmodels.properties

typealias InputTextEditorActionBlock = (actionContext: Any?) -> Boolean

open class InputTextEditorAction(private var action: InputTextEditorActionBlock) {
    fun execute(): Boolean = execute(null)

    open fun execute(actionContext: Any? = null): Boolean = action(actionContext)

    companion object {
        val None = InputTextEditorAction { false }
    }
}
