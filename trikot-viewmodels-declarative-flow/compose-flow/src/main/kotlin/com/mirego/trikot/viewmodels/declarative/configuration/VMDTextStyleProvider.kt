package com.mirego.trikot.viewmodels.declarative.configuration

import androidx.compose.ui.text.TextStyle
import com.mirego.trikot.viewmodels.declarative.properties.VMDTextStyleResource

interface VMDTextStyleProvider {
    fun textStyleForResource(resource: VMDTextStyleResource): TextStyle?
}

class DefaultTextStyleProvider : VMDTextStyleProvider {
    override fun textStyleForResource(resource: VMDTextStyleResource): TextStyle? = null
}
