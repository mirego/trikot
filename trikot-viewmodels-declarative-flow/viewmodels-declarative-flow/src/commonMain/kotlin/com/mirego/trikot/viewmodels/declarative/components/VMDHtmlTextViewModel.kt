package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel

typealias VMDUrlActionBlock = (url: String) -> Unit

interface VMDHtmlTextViewModel : VMDViewModel {
    val html: String
    val urlActionBlock: VMDUrlActionBlock?
}
