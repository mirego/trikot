package com.mirego.sample.resource

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.mirego.sample.resources.SampleTextStyleResource
import com.mirego.trikot.viewmodels.declarative.configuration.VMDTextStyleProvider
import com.mirego.trikot.viewmodels.declarative.properties.VMDNoTextStyleResource
import com.mirego.trikot.viewmodels.declarative.properties.VMDTextStyleResource

class SampleTextStyleProvider : VMDTextStyleProvider {
    @Composable
    override fun textStyleForResource(resource: VMDTextStyleResource): TextStyle? =
        when (resource) {
            is VMDNoTextStyleResource -> null
            is SampleTextStyleResource -> mapSampleStyleResource(resource)
            else -> null
        }

    @Composable
    private fun mapSampleStyleResource(resource: SampleTextStyleResource): TextStyle =
        when (resource) {
            SampleTextStyleResource.HIGHLIGHTED -> TextStyle(background = Color.Yellow)
        }
}
