package com.trikot.viewmodels.sample.resource

import android.content.Context
import android.text.style.TextAppearanceSpan
import com.mirego.trikot.viewmodels.resource.TextAppearanceResource
import com.mirego.trikot.viewmodels.resources.TextAppearanceSpanResourceProvider
import com.trikot.viewmodels.sample.R

class SampleTextAppearanceResourceProvider : TextAppearanceSpanResourceProvider {
    override fun spanFromResource(
        resource: TextAppearanceResource,
        context: Context
    ): TextAppearanceSpan? {
        if (resource == TextAppearanceResource.None) return null
        return when (resource as SampleTextAppearanceResource) {
            SampleTextAppearanceResource.TEXT_APPEARANCE_BOLD ->
                R.style.SampleTextAppearanceBold
            SampleTextAppearanceResource.TEXT_APPEARANCE_ITALIC ->
                R.style.SampleTextAppearanceItalic
            SampleTextAppearanceResource.TEXT_APPEARANCE_COLORED ->
                R.style.SampleTextAppearanceColored
            SampleTextAppearanceResource.TEXT_APPEARANCE_GRAYED ->
                R.style.SampleTextAppearanceGrayed
            SampleTextAppearanceResource.TEXT_APPEARANCE_HIGHLIGHTED ->
                0 // Not supported on Android
            else -> 0 // Nothing
        }.toSpan(context)
    }

    private fun Int.toSpan(context: Context): TextAppearanceSpan = TextAppearanceSpan(context, this)
}
