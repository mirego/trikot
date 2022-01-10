package com.mirego.trikot.viewmodels.resources

import android.content.Context
import android.text.ParcelableSpan
import com.mirego.trikot.viewmodels.resource.TextAppearanceResource

interface TextAppearanceSpanResourceProvider {
    fun spanFromResource(
        resource: TextAppearanceResource,
        context: Context
    ): ParcelableSpan?
}

object TextAppearanceSpanResourceManager {
    var provider: TextAppearanceSpanResourceProvider =
        DefaultTextAppearanceSpanResourceProvider()
}

class DefaultTextAppearanceSpanResourceProvider : TextAppearanceSpanResourceProvider {
    override fun spanFromResource(
        resource: TextAppearanceResource,
        context: Context
    ): ParcelableSpan? = null
}
