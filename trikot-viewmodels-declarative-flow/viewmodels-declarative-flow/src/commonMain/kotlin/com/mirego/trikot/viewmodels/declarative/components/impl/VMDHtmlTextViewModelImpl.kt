package com.mirego.trikot.viewmodels.declarative.components.impl

import com.mirego.trikot.foundation.concurrent.atomic
import com.mirego.trikot.viewmodels.declarative.components.VMDHtmlTextViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDUrlActionBlock
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDFlowProperty
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.emit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

open class VMDHtmlTextViewModelImpl(coroutineScope: CoroutineScope) :
    VMDViewModelImpl(coroutineScope), VMDHtmlTextViewModel {

    private val htmlDelegate = emit("", this, coroutineScope)
    override var html: String by htmlDelegate

    override var urlActionBlock: VMDUrlActionBlock? by atomic(null)

    fun bindHtml(flow: Flow<String>) {
        updateProperty(this::html, flow)
    }

    fun setUrlAction(urlAction: VMDUrlActionBlock) {
        urlActionBlock = { url ->
            urlAction.invoke(url)
        }
    }

    fun <T> setUrlAction(flow: Flow<T>, action: VMDUrlActionBlock) {
        urlActionBlock = { url ->
            coroutineScope.launch {
                flow.firstOrNull()?.let { action(url) }
            }
        }
    }

    override val propertyMapping: Map<String, VMDFlowProperty<*>> by lazy {
        super.propertyMapping.toMutableMap().also {
            it[this::html.name] = htmlDelegate
        }
    }
}
