package com.mirego.trikot.viewmodels.declarative.configuration

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import com.mirego.trikot.viewmodels.declarative.properties.VMDTextStyleResource

interface VMDTextStyleProvider {
    @Composable
    fun textStyleForResource(resource: VMDTextStyleResource): TextStyle?
}

class DefaultTextStyleProvider : VMDTextStyleProvider {
    @Composable
    override fun textStyleForResource(resource: VMDTextStyleResource): TextStyle? = null
}
